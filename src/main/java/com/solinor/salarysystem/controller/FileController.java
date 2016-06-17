package com.solinor.salarysystem.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.solinor.salarysystem.dao.WorkingHourDao;
import com.solinor.salarysystem.model.Workinghour;
import com.solinor.salarysystem.util.Constants;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Solinor Salary System Implementation.
 * 
 * File Controller
 * The File Controller contains the routes that handle uploading and parsing the csv file and insert the parsed data to the Database. 
 * 
 * @category 	Controller.
 * @package 	com.solinor.salarysystem.controller.
 * @author  	Furqan Ahmed <ahmedfurqan88@gmail.com>
 * @license     http://opensource.org/licenses/osl-3.0.php  Open Software License (OSL 3.0)
 * */

@Controller
public class FileController {

	private WorkingHourDao workingHourDao;
	
	//Autowiring WorkingHourDao.
	@Autowired(required=true)
	@Qualifier(value="WorkingHourDAO")
	public void setWorkingHourDao(WorkingHourDao workingHourDao) {
		this.workingHourDao = workingHourDao;
	}
	
	/**
	Request mapping for upload.jsp.
	@param ModelMap model
	@return String
	*/
	@RequestMapping( value="/uploadcsv", method=RequestMethod.GET )
	public String csvUpload( ModelMap model ) {
		return "upload";
	}
	
	/**
	Provide Request Mapping for uploadfile additionally parse the csv file and bulk insert the date to the database.
	@param HttpServletRequest  request
	@return String
	@throws IOException
	*/
	@SuppressWarnings("resource")
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public String uploadFile(ModelMap model, @RequestParam MultipartFile file, HttpServletRequest request) {
		
		//empty file check
		if (file.isEmpty()) {
			model.put("error", "No File Choosen, Please go back and select a file to Upload.");
			return "error";
		}
		
		//root path of the csv file that is uploaded.
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		//file uploaded to the server.
		File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		
		//Getting file extension to check for csv file extension.
		String ext = FilenameUtils.getExtension(serverFile.getPath());
		
		//if the extension is other than csv redirect to error page.
		if(!ext.equals("csv")) {
			model.put("error", "Only CSV file are allowed, Please go back and select a csv file to Upload.");
			return "error";
		}
		
		try {
			InputStream is = file.getInputStream();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			int i;
			//write file to server
			while ((i = is.read()) != -1) {
				stream.write(i);
			}
			stream.flush();
		} catch (IOException e) {
			model.put("error", "Unable to process file because : " + e.getMessage());
			return "error";
		}

		String[] nextLine;
		List<Workinghour> workingHour = new ArrayList<Workinghour>();

		try {
			//read file
			//CSVReader(fileReader, ';', '\'', 1) means
			//using separator ; and using single quote ' . Skip first line when read

			FileReader fileReader = new FileReader(serverFile);
			CSVReader reader = new CSVReader(fileReader, ';', '\'', 1);
			while ((nextLine = reader.readNext()) != null) {
				//splitting the comma separated line.
				String[] data = nextLine[0].split(",");

				String starttime = data[3];
				String endtime	 = data[4];
				
				//calculating the employee's total work hours for the day. 
				int totalWorkingHours = calculateTotalHoursWorked(starttime, endtime);
				
				//calculating the regular pay of the employee without the compensation.
				double regularPay = calculateRegularDailyWage(totalWorkingHours);
				
				//calculating employee's overtime compensation for the day.
				double overtimeCompensation = calculateOvertimeCompensation(totalWorkingHours);
				
				//calculating employee's evening compensation for the day. 
				Double eveningCompensation = calculateEveningCompensation(starttime, endtime);
				
				//calculating the total salary by adding the regular pay and the compensations.
				double currentDaySalary = regularPay+overtimeCompensation+eveningCompensation;

				//preparing the object.
				Workinghour wh = new Workinghour();

				wh.setName(data[0]);
				wh.setId(data[1]);
				wh.setDate(data[2]);
				wh.setStarttime(data[3]);
				wh.setEndtime(data[4]);
				wh.setEvening_compensation(eveningCompensation);
				wh.setOvertime_compensation(overtimeCompensation);
				wh.setTotal_daily_pay(currentDaySalary);

				//adding to the list.
				workingHour.add(wh);
			}

		} catch (IOException e) {
			System.out.println("error while reading csv and put to db : " + e.getMessage());
		} 		

		//inserting the bulk data as a batch to the database.
		this.workingHourDao.insertBatch(workingHour);

		return "salaryviewer";
	}
	
	/**
	Returns the total number of hours worked by the employee.
	@param String starttime, String endtime
	@return int
	@throws Exception e
	*/
	private int calculateTotalHoursWorked(String starttime, String endtime) {

		int hours = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			
			//parsing the start and end time in 24hrs format
			Date startdate	= format.parse(starttime);
			Date enddate	= format.parse(endtime);

			//difference of the start and endtime.
			long difference = enddate.getTime() - startdate.getTime();
			
			//converting the time difference in ms to hours.
			hours = (int)((difference / (1000*60*60)) % 24);
			
			/*
			 * if the time difference is in negative the working hours enters into the midnight, so calculating the previous
			 * day hours and the next day hours.
			*/
			if(hours<0) {
				long previousDayHoursDifference = format.parse("24:00").getTime() - startdate.getTime() ;
				int previousDayHours = (int)((previousDayHoursDifference / (1000*60*60)) % 24);
				int nextDayHours = format.parse(endtime).getHours();
				hours = previousDayHours + nextDayHours;
			}

		} catch(Exception e) {
			System.out.println("Unable to parse "+e.getMessage());
		}
		return hours;
	}
	
	/**
	Calculate the Regular Daily Wage
	@param int totalWorkingHours
	@return double
	*/
	private double calculateRegularDailyWage(int totalWorkingHours) {

		double regularWage = 0;
		double hourlyWage = Constants.HOURLY_WAGE;
		int actualWorkingHour = 0;
		
		//if the working hours are greater than 8 hours calculate the 8 hours wage.
		if( totalWorkingHours>8 ) {
			int overtimeHours = totalWorkingHours - 8;
			actualWorkingHour = totalWorkingHours - overtimeHours;

			regularWage = actualWorkingHour * hourlyWage;
		} else { //else calculate the normal hours working wage.
			regularWage = totalWorkingHours * hourlyWage;
		}

		return regularWage; 
	}
	
	/**
	Returns the Total Overtime Compensation of the employee..
	@param HttpServletRequest  request
	*/
	private double calculateOvertimeCompensation(int totalWorkingHours) {

		double overtimeAmount = 0;

		//if working hours exceeds normal 8 hours.
		if(totalWorkingHours>8) {

			//total calculated overtime hours.
			int overtimeHours = totalWorkingHours - 8;
			
			
			/*
			 * First 2 Hours>8Hours=HourlyWage+25%
			 * Next 2 Hours = Hourly Wage + 50%
			 * After That = Hourly Wage + 100%
			 * */
			if( overtimeHours<= 2) {
				overtimeAmount = Constants.OVERTIME_COMPENSATION_2HOURS * overtimeHours;
			} else if (overtimeHours>2 && overtimeHours<=4) {
				overtimeAmount = Constants.OVERTIME_COMPENSATION_NEXT2HOURS * overtimeHours;
			} else if (overtimeHours>4) {
				overtimeAmount = Constants.OVERTIME_COMPENSATION_AFTERTHAT * overtimeHours;
			}
		}

		return overtimeAmount;
	}
	
	/**
	Calculate the evening compensation for the employee
	@param String starttime, String endtime
	@return String
	@throws Exception e
	*/
	private Double calculateEveningCompensation(String startTime, String endTime) {

		Double eveningCompensationAmount = 0.0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("HH");

			Date eveningHourStartTime 	= format.parse(Constants.EVENING_HOUR_START_TIME );
			Date eveningHourEndTime 	= format.parse(Constants.EVENING_HOUR_END_TIME );
			Date start					= format.parse(startTime);
			Date end					= format.parse(endTime);
			
			//if the start time lies outside the evening hours
			if( start.getTime()<eveningHourStartTime.getTime()){

				int hoursDifference = (int) (((end.getTime() - eveningHourStartTime.getTime())/(1000*60*60))%24);
				if (hoursDifference>0) {
					eveningCompensationAmount = hoursDifference * Constants.EVENING_COMPENSATION_RATE;
				} else if ( start.getHours()>=format.parse("00:00").getHours() && start.getHours()<=format.parse("06:00").getHours() ) {
					int diff = format.parse("06:00").getHours() - start.getHours();

					DecimalFormat df = new DecimalFormat("####0.00");
					eveningCompensationAmount = Double.parseDouble(df.format(diff * Constants.EVENING_COMPENSATION_RATE));
				}
			} else if (start.getTime()>eveningHourStartTime.getTime()) { //if the start time lies inside the evening hours
				int startEndHoursDifference = (int) (((end.getTime() - start.getTime())/(1000*60*60))%24);
		
				//if the hours enters into the midnight then calculate the prev and next day hours.
				if(startEndHoursDifference<0) {
					long previousDayHoursDifference = format.parse("24:00").getTime() - start.getTime() ;
					int previousDayHours = (int)((previousDayHoursDifference / (1000*60*60)) % 24);
					int nextDayHours = end.getHours();

					eveningCompensationAmount = (previousDayHours + nextDayHours) * Constants.EVENING_COMPENSATION_RATE;
				} else {
					int totalHours = (int) (((end.getTime() - start.getTime())/(1000*60*60))%24);
					eveningCompensationAmount = totalHours * Constants.EVENING_COMPENSATION_RATE;
				}
			}
		} catch(Exception e) {
			System.out.println("Unable to parse "+e.getMessage());
		}
		return eveningCompensationAmount;
	}
}