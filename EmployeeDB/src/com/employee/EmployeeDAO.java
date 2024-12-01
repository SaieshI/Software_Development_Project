package com.employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.employee.model.Employee;
import com.employee.model.Salary;

public class EmployeeDAO {
	//Database connection object
	Connection con= null;
	
	/**
	 * Method that establishes a connection to database
	 */
	public Connection getConnection() throws Exception { 
		
		try {
			//Load MySQL JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");  
			
			//Makes connection with the database
			 con=DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/Soft_Dev_Project", //Database URL changes based on what you named your own database
			"root", //Database username 
			"Saiesh1234" //Database password is different based on what you put as your username 
			); 
			
			
		}catch(Exception e) {
			//Generate exception for further handling 
			throw e;
		}
		//Return connection object
		return con;
	}
	/**
	 *  Program of using connection and inputing data into database 
	 */
	public static void main(String[] str) {
		System.out.println("This is my Database connection file");
		
		//Make a new Employee object with data 
		Employee employee = new Employee();
		employee.setEmployeeid(1001);
		employee.setFirstName("Prasad");
		employee.setLastName("Irukulla");
		employee.setJobTitle("Programmer");
		employee.setSsn("1234");
		employee.setDepartment("Marketing");
		
		//Prepare salary object and add to the list //
		Salary salary = new Salary();
		salary.setSalaryid(101);
		//Employee and salary one to many
		salary.setEmployeeid(1001);
		salary.setSalarydate(new Date());
		salary.setEmpSalary(8000);
		
		List<Salary> salaryList = new ArrayList<Salary>();
		salaryList.add(salary);
		
		
		//Add Salary list to employee object
		employee.setSalaryList(salaryList);
		
		EmployeeDAO empDao = new EmployeeDAO();
		
		//Make EmployeeDAO object that interacts with the database
		empDao.createEmployee(employee);
		
	}
	/**
	 * Method to insert information in Employee's table 
	 */
	public void createEmployee(Employee employee) {
		System.out.println(employee.toString()); //Print employee details 
		ResultSet rs = null;
		try {
			//SQL query to insert information into table 
		    String query1 = "insert into Employee (employee_id, first_name, last_name, ssn, job_title, department)  values(?, ?, ?, ?, ?, ?)";
		    //Get database connection 
	        con = getConnection();
	        // Prepare Statement
		    PreparedStatement myStmt
	            = con.prepareStatement(query1);
	        // Set Parameters
	        myStmt.setInt(1, employee.getEmployeeid());
	        myStmt.setString(2, employee.getFirstName());
	        myStmt.setString(3, employee.getLastName()); 
	        myStmt.setString(4, employee.getSsn()); 
	        myStmt.setString(5, employee.getJobTitle());
	        myStmt.setString(6, employee.getDepartment());
	        // Execute SQL query
	        int res = myStmt.executeUpdate();	
	        
	        setSalary(employee.getSalaryList().get(0));
	        
		} catch (Exception e) {
			//Print in case of error
			e.printStackTrace();
		}  
	}
	
	/**
	 * Method to insert infromation into Salary table
	 */
	public void setSalary(Salary salary) { 
		System.out.println(salary.toString()); //Print salary details
		ResultSet rs = null; 
		try { 
			//SQL query to insert information into table 
			String query2 = "insert into Salary (salary_id, employee_id, emp_salary, salary_date) values(?, ?, ?, ?)";
			//Get database connection
			 con = getConnection();
			 // Prepare Statement
			PreparedStatement myStmt
				= con.prepareStatement(query2); 
			// Set Parameters
			myStmt.setInt(1, salary.getSalaryid());
			myStmt.setInt(2, salary.getEmployeeid());
			myStmt.setDouble(3, salary.getEmpsalary());
			//Covert Java Util date to SQL Date
			myStmt.setDate(4, new java.sql.Date(salary.getSalarydate().getTime())); 
			//Execute SQL query
			int res = myStmt.executeUpdate(); 
		} catch (Exception e) {
			//Print in case of error 
			e.printStackTrace();
		}  
	}
	
}
