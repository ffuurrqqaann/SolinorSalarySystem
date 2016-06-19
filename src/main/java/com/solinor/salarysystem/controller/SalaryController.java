package com.solinor.salarysystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.solinor.salarysystem.dao.WorkingHourDao;
import com.solinor.salarysystem.model.EmployeeSalaryJsonObject;
import com.solinor.salarysystem.model.MonthlySalary;

/**
 * Solinor Salary System Implementation.
 * 
 * Salary Controller
 * The Salary Controller contains the routes to view the Monthly Salary of the Employees along with the compensations.
 * 
 * @category 	Controller.
 * @package 	com.solinor.salarysystem.controller.
 * @author  	Furqan Ahmed <ahmedfurqan88@gmail.com>
 * @license     http://opensource.org/licenses/osl-3.0.php  Open Software License (OSL 3.0)
 * */
@Controller
public class SalaryController {
	
	private WorkingHourDao workingHourDao; 
	
	//autowiring the workinghour dao.
	@Autowired(required=true)
	@Qualifier(value="WorkingHourDAO")
	public void setWorkingHourDao(WorkingHourDao workingHourDao) {
		this.workingHourDao = workingHourDao;
	}
	
	/**
	Request Mapping for salaryviewer.jsp page
	@param ModelMap model
	@return String
	*/
	@RequestMapping( value="/salaryviewer", method=RequestMethod.GET )
	public String salaryViewer( ModelMap model ) {
		return "salaryviewer";
	}
	
	/**
	Returns the Total Monthly Salary of Employees along with the compensations.
	@param HttpServletRequest  request
	@return String
	@throws IOException
	*/
	@RequestMapping(value = "/getMonthlySalaryRecords/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String springPaginationDataTables(HttpServletRequest  request) throws IOException {
		
		//Fetch the page number from the client
    	Integer pageNumber = 0;
    	if (null != request.getParameter("iDisplayStart"))
    		pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/10)+1;
    	
    	//Fetch search parameter
    	String searchParameter = request.getParameter("sSearch");
    	
    	//Fetch Page display length
    	Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
    	
    	//Create page list data
    	List<MonthlySalary> monthlySalaryList = this.workingHourDao.getEmployeesMonthlySalary();
    	
    	//Here is server side pagination logic. Based on the page number you could make call 
    	//to the data base create new list and send back to the client. For demo I am shuffling 
    	//the same list to show data randomly
    	if (pageNumber == 1) {
    		Collections.shuffle(monthlySalaryList);
    	}else if (pageNumber == 2) {
    		Collections.shuffle(monthlySalaryList);
    	}else {
    		Collections.shuffle(monthlySalaryList);
    	}
    	
    	//get search records.
    	monthlySalaryList = getSalaryBasedOnSearchParameter(searchParameter,monthlySalaryList);
    	
    	EmployeeSalaryJsonObject empSalaryJson = new EmployeeSalaryJsonObject();
    	
    	//setting the total display records size.
    	empSalaryJson.setiTotalDisplayRecords(monthlySalaryList.size());
    	
    	//setting the total records size.
    	empSalaryJson.setiTotalRecords(monthlySalaryList.size());
    	
    	//setting the data for the salary datatable
    	empSalaryJson.setAaData(monthlySalaryList);
    	
    	//encoding the records to JSON.
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String json = gson.toJson(empSalaryJson);
		
    	return json;
		
	}
	
	/**
	Returns the record as per search String 
	@param String searchParam
	@param List<MonthlySalary> monthlySalaryList
	@return List<MonthlySalary>
	*/
	private List<MonthlySalary> getSalaryBasedOnSearchParameter(String searchParam, List<MonthlySalary> monthlySalaryList) {
		
		//if the user search for the record.
		if (null != searchParam && !searchParam.equals("")) {
			List<MonthlySalary> salaryListForSearch = new ArrayList<MonthlySalary>();
			
			//converting the search string to uppercase
			searchParam = searchParam.toUpperCase();
			
			for (MonthlySalary salary : monthlySalaryList) {
				
				if( salary.getName().toUpperCase().indexOf(searchParam)!=-1 ||
					salary.getEvening_compensation().toUpperCase().indexOf(searchParam)!=-1 ||
					salary.getOvertime_compensation().toUpperCase().indexOf(searchParam)!=-1||
					salary.getTotal_pay().toUpperCase().indexOf(searchParam)!=-1 ) {
					
					//populating the list as per the search string.
					salaryListForSearch.add(salary);
				}
			}
			monthlySalaryList = salaryListForSearch;
			salaryListForSearch = null;
		}
		return monthlySalaryList;
	}	
}