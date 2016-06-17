package com.solinor.salarysystem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.Assert;

import com.solinor.salarysystem.util.Constants;

public class SalaryViewerTests  {
	
	@Test
	public void testCalculateRegularDailyWageFor8Hours() {
		
		String startTime 	= "08:30";
		String endTime 		= "16:30";
		
		int totalWorkingHours 	= calculateTotalHoursWorked(startTime, endTime);
		Double regularWage 		= calculateRegularDailyWage(totalWorkingHours);		
		/*
		 * Setting the wage to 30$ hours i.e., 3.75*8 = 30.
		 * */
		Assert.assertEquals("30.0", Double.toString(regularWage)); 
	}
	
	@Test
	public void testCalculateRegularDailyWageForLessThan8Hours() {
		
		String startTime 	= "05:00";
		String endTime 		= "10:00";
		
		int totalWorkingHours 	= calculateTotalHoursWorked(startTime, endTime);
		Double regularWage 		= calculateRegularDailyWage(totalWorkingHours);
		
		/*
		 * Setting the wage to 18.75$ i.e., 3.75*5 = 18.75.
		 * */
		Assert.assertEquals("18.75", Double.toString(regularWage));
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
	
	@Test
	public void testCalculateOvertimeCompensationAfterThatHours() {
		
		String startTime 	= "08:00";
		String endTime 		= "22:00";
		
		int totalWorkingHours = calculateTotalHoursWorked(startTime, endTime);
		Double overtimeAmount = calculateOvertimeCompensation(totalWorkingHours);
		
		/*
		 * Setting the overtime compensation to 22.5$ i.e., (3.75*2)*6 = 45.0, 
		 * */
		Assert.assertEquals("45.0", Double.toString(overtimeAmount));
	}
	
	@Test
	public void testCalculateOvertimeCompensationTwoHours() {
		
		String startTime 	= "08:00";
		String endTime 		= "18:00";
		
		int totalWorkingHours = calculateTotalHoursWorked(startTime, endTime);
		Double overtimeAmount = calculateOvertimeCompensation(totalWorkingHours);
		
		//restricting to two decimal places.
		DecimalFormat df = new DecimalFormat("####0.00");
		overtimeAmount = Double.parseDouble(df.format(overtimeAmount));
		
		/*
		 * Setting the overtime compensation to 9.38$ i.e., ((3.75*25%)+3.75)*2 = 9.38, 
		 * */
		Assert.assertEquals("9.38", Double.toString(overtimeAmount));
	}
	
	@Test
	public void testCalculateOvertimeCompensationAfterTwoHours() {
		
		String startTime 	= "08:00";
		String endTime 		= "19:00";
		
		int totalWorkingHours = calculateTotalHoursWorked(startTime, endTime);
		Double overtimeAmount = calculateOvertimeCompensation(totalWorkingHours);
		
		//restricting to two decimal places.
		DecimalFormat df = new DecimalFormat("####0.00");
		overtimeAmount = Double.parseDouble(df.format(overtimeAmount));
		
		/*
		 * Setting the overtime compensation to 22.5$ i.e., (3.75*2)*6 = 45.0, 
		 * */
		Assert.assertEquals("16.88", Double.toString(overtimeAmount));
	}
	
	@Test
	public void testCalculateEveningCompensation() {
		
		String startTime	= "08:30";
		String endTime		= "20:00";
		
		Double eveningCompensationAmount = calculateEveningCompensation(startTime, endTime);
				
		//setting the two hours evening hours, 1.15*2 = 2.3
		Assert.assertEquals("2.3", Double.toString(eveningCompensationAmount));
	}

	@Test
	public void testCalculateTotalPay() {
		
		String startTime	= "08:00";
		String endTime		= "21:00";
		
		// 5 hrs overtime = 37.5, 8 hours normal work = 30, 3 hr evening compensation = 3.45
		int totalWorkingHours = calculateTotalHoursWorked(startTime, endTime);
		Double regularPay 			= calculateRegularDailyWage(totalWorkingHours);
		Double overtimeCompensation = calculateOvertimeCompensation(totalWorkingHours);
		Double eveningCompensation	= calculateEveningCompensation(startTime, endTime);
		
		Double totalPay = regularPay+overtimeCompensation+eveningCompensation;
		
		// 5 hrs overtime = 37.5, 8 hours normal work = 30, 3 hr evening compensation = 3.45
		Assert.assertEquals("70.95", Double.toString(totalPay));
		
	}
	
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


}