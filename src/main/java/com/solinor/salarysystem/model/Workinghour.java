package com.solinor.salarysystem.model;

/**
 * Solinor Salary System Implementation.
 * 
 * Employee Monthly Salary Model
 * The model file represent the Employee's information along with its working hours for the day.  
 * 
 * @category 	model.
 * @package 	com.solinor.salarysystem.model.
 * @author  	Furqan Ahmed <ahmedfurqan88@gmail.com>
 * @license     http://opensource.org/licenses/osl-3.0.php  Open Software License (OSL 3.0)
 * */

public class Workinghour {
	
	private String id;
	private String name;
	private String date;
	private String starttime;
	private String endtime;
	
	private double evening_compensation;
	private double overtime_compensation;
	private double total_daily_pay;
	
	/**
	* The id of the employee.
	* @HasGetter
	* @HasSetter
	*/
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	* The name of the employee.
	* @HasGetter
	* @HasSetter
	*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	* The working day's start time.
	* @HasGetter
	* @HasSetter
	*/
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	
	/**
	* The working day's end time.
	* @HasGetter
	* @HasSetter
	*/
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
	/**
	* The date of the month.
	* @HasGetter
	* @HasSetter
	*/
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	* Evening Compensation for the day's work hours.
	* @HasGetter
	* @HasSetter
	*/
	public double getEvening_compensation() {
		return evening_compensation;
	}
	public void setEvening_compensation(double evening_compensation) {
		this.evening_compensation = evening_compensation;
	}
	
	/**
	* The Overtime Compensation for the day's work hours.
	* @HasGetter
	* @HasSetter
	*/
	public double getOvertime_compensation() {
		return overtime_compensation;
	}
	public void setOvertime_compensation(double overtime_compensation) {
		this.overtime_compensation = overtime_compensation;
	}
	
	/**
	* The Total salary for the day.
	* @HasGetter
	* @HasSetter
	*/
	public double getTotal_daily_pay() {
		return total_daily_pay;
	}
	public void setTotal_daily_pay(double total_daily_pay) {
		this.total_daily_pay = total_daily_pay;
	}	
}