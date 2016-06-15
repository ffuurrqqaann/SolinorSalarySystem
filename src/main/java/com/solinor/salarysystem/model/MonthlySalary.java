package com.solinor.salarysystem.model;

/**
 * Solinor Salary System Implementation.
 * 
 * Employee Monthly Salary Model
 * The model file contains the getter/setters for the employee's monthly Salary model.  
 * 
 * @category 	model.
 * @package 	com.solinor.salarysystem.model.
 * @author  	Furqan Ahmed <ahmedfurqan88@gmail.com>
 * @license     http://opensource.org/licenses/osl-3.0.php  Open Software License (OSL 3.0)
 * */

public class MonthlySalary {
	
	private int id;
	
	private String name;
	
	private double evening_compensation;
	private double overtime_compensation;
	private double total_pay;
	
	/**
	* The id of the employee.
	* @HasGetter
	* @HasSetter
	*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	* The Month's Evening Compensation of the Employee. 
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
	* The Month's Overtime Compensation of the Employee.
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
	* The Month's Total pay of the Employee.
	* @HasGetter
	* @HasSetter
	*/
	public double getTotal_pay() {
		return total_pay;
	}
	public void setTotal_pay(double total_pay) {
		this.total_pay = total_pay;
	}	
}