package com.solinor.salarysystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.solinor.salarysystem.model.MonthlySalary;
import com.solinor.salarysystem.model.Workinghour;


/**
 * Solinor Salary System Implementation.
 * 
 * Working Hour Dao Implementation class
 * The implementation contains the insert, insert batch method to insert the bulk data and getEmployeesMonthlySalary() to fetch 
 * the total monthly salary of the employees along with the compensations.  
 * 
 * @category 	dao.
 * @package 	com.solinor.salarysystem.dao.
 * @author  	Furqan Ahmed <ahmedfurqan88@gmail.com>
 * @license     http://opensource.org/licenses/osl-3.0.php  Open Software License (OSL 3.0)
 * */

public class WorkingHourDaoImpl implements WorkingHourDao{
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void insert(Workinghour workinghour) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO workinghours " +
				"(name, starttime, endtime) VALUES (?, ?, ?)";
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, workinghour.getName());
			ps.setString(2, workinghour.getStarttime());
			ps.setString(3, workinghour.getEndtime());
			
			ps.executeUpdate();
			
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	public void insertBatch(final List<Workinghour> workingHours) {
		// TODO Auto-generated method stub
		
		String sql = "INSERT INTO workinghours " +
				"(id, name, date, starttime, endtime, evening_compensation, overtime_compensation, total_daily_pay) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int count) throws SQLException {
				// TODO Auto-generated method stub
				Workinghour workingHour = workingHours.get(count);
				
				ps.setInt(1, Integer.parseInt(workingHour.getId()));
				ps.setString(2, workingHour.getName());
				ps.setString(3, workingHour.getDate());
				ps.setString(4, workingHour.getStarttime());
				ps.setString(5, workingHour.getEndtime());
				ps.setString(6, String.valueOf(workingHour.getEvening_compensation()));
				ps.setString(7, String.valueOf(workingHour.getOvertime_compensation()));
				ps.setString(8, String.valueOf(workingHour.getTotal_daily_pay()));
				
			}
			
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return workingHours.size();
			}
		});
	}
	
	private JdbcTemplate getJdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		
		return jdbcTemplate;
	}

	public List<MonthlySalary> getEmployeesMonthlySalary() {
		// TODO Auto-generated method stub
		String sql = "SELECT id, name, SUM(evening_compensation) AS evening_compensation, SUM(overtime_compensation) AS overtime_compensation, SUM(total_daily_pay) AS total_pay "+
				"FROM workinghours"+
				" GROUP BY id";
		
		System.out.println("sql is = "+sql);
		
		Connection conn = null;
		Statement stmnt = null;
		
		List<MonthlySalary> monthlySalaryList = new ArrayList<MonthlySalary>();
		try {
			
			conn 	= dataSource.getConnection();
			stmnt 	= conn.createStatement();
			
			ResultSet rs = stmnt.executeQuery(sql);
			
			while(rs.next()){
		         //Retrieve by column name
		         
				MonthlySalary monthlySalary = new MonthlySalary();
				
				monthlySalary.setId(rs.getInt("id"));
				monthlySalary.setName(rs.getString("name"));
				monthlySalary.setEvening_compensation(rs.getDouble("evening_compensation"));
				monthlySalary.setOvertime_compensation(rs.getDouble("overtime_compensation"));
				monthlySalary.setTotal_pay(rs.getDouble("total_pay"));
				
				monthlySalaryList.add(monthlySalary);
		      }
		      rs.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		
		return monthlySalaryList;
	}
}