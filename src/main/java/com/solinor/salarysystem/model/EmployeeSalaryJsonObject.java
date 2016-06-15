package com.solinor.salarysystem.model;

import java.util.List;

/**
 * Solinor Salary System Implementation.
 * 
 * Employee Salary Json Object
 * The model file contains the Json model for Employee Salary Data Table grid.  
 * 
 * @category 	model.
 * @package 	com.solinor.salarysystem.model.
 * @author  	Furqan Ahmed <ahmedfurqan88@gmail.com>
 * @license     http://opensource.org/licenses/osl-3.0.php  Open Software License (OSL 3.0)
 * */

public class EmployeeSalaryJsonObject {
	int iTotalRecords;
	int iTotalDisplayRecords;

	String sEcho;
	String sColumns;

	private List<MonthlySalary> aaData;
	
	/**
	* Total Number of records for the Employee Salary Data Table grid.
	* @HasGetter
	* @HasSetter
	*/
	public int getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	/**
	* Total Number of Display records for the Employee Salary Data Table grid.
	* @HasGetter
	* @HasSetter
	*/
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	
	/**
	* sEcho for the Employee Salary Data Table grid.
	* @HasGetter
	* @HasSetter
	*/
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	
	/**
	* Coloumn for the Employee Salary Data Table grid.
	* @HasGetter
	* @HasSetter
	*/
	public String getsColumns() {
		return sColumns;
	}
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	
	/**
	* Data for the Employee Salary Data Table grid.
	* @HasGetter
	* @HasSetter
	*/
	public List<MonthlySalary> getAaData() {
		return aaData;
	}
	public void setAaData(List<MonthlySalary> aaData) {
		this.aaData = aaData;
	}
}