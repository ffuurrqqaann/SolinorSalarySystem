package com.solinor.salarysystem.dao;

import java.util.List;

import com.solinor.salarysystem.model.MonthlySalary;
import com.solinor.salarysystem.model.Workinghour;

/**
 * Solinor Salary System Implementation.
 * 
 * Working Hour Dao Interface
 * The interface contains the insert, insert batch method to insert the bulk data and getEmployeesMonthlySalary() to fetch 
 * the total monthly salary of the employees along with the compensations.  
 * 
 * @category 	dao.
 * @package 	com.solinor.salarysystem.dao.
 * @author  	Furqan Ahmed <ahmedfurqan88@gmail.com>
 * @license     http://opensource.org/licenses/osl-3.0.php  Open Software License (OSL 3.0)
 * */


public interface WorkingHourDao {
	public void insert( Workinghour workinghour );
	public void insertBatch(List<Workinghour> workingHours);
	public List<MonthlySalary> getEmployeesMonthlySalary();	
}
