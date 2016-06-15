package com.solinor.salarysystem.util;

/**
 * Solinor Salary System Implementation.
 * 
 * Constants
 * The File contains constant values for the Salary System.
 * 
 * @category 	Util.
 * @package 	com.solinor.salarysystem.util.
 * @author  	Furqan Ahmed <ahmedfurqan88@gmail.com>
 * @license     http://opensource.org/licenses/osl-3.0.php  Open Software License (OSL 3.0)
 * */
public class Constants {
	
	public static final double HOURLY_WAGE = 3.75;
	
	public static final double OVERTIME_COMPENSATION_2HOURS		= ((double)25/(double)100) * HOURLY_WAGE + HOURLY_WAGE;
	public static final double OVERTIME_COMPENSATION_NEXT2HOURS	= ((double)50/(double)100) * HOURLY_WAGE + HOURLY_WAGE;
	public static final double OVERTIME_COMPENSATION_AFTERTHAT	= ((double)100/(double)100) * HOURLY_WAGE + HOURLY_WAGE;
	
	public static final double EVENING_COMPENSATION_RATE = 1.15;
	public static final String EVENING_HOUR_START_TIME   = "18:00";
	public static final String EVENING_HOUR_END_TIME	 = "06:00";
	
}