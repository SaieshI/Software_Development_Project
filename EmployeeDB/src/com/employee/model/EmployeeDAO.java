package com.employee.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import EmployeeDB.src.com.employee.model.Employee;
import EmployeeDB.src.com.employee.model.Salary;

public class EmployeeDAO {
    // Database connection object
    Connection con = null;

    /**
     * Method that establishes a connection to the database
     */
    public Connection getConnection() throws Exception {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Make connection with the database
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Soft_Dev_Project", // Database URL changes based on what you named your own database
                "root", // Database username
                "Saiesh1234" // Database password is different based on what you put as your username
            );
        } catch (Exception e) {
            // Generate exception for further handling
            throw e;
        }
        // Return connection object
        return con;
    }

    /**
     * Main method for testing database interactions
     * @throws Exception 
     */
    public static void main(String[] str) throws Exception {
        System.out.println("This is my Database connection file");

        // Make a new Employee object with data
        Employee employee = new Employee();
        employee.setEmployeeid(1001);
        employee.setFirstName("Prasad");
        employee.setLastName("Irukulla");
        employee.setJobTitle("Programmer");
        employee.setSsn("1234");
        employee.setDepartment("Marketing");

        // Prepare salary object and add to the list
        Salary salary = new Salary();
        salary.setSalaryid(101);
        // Employee and salary one-to-many
        salary.setEmployeeid(1001);
        salary.setSalarydate(new Date());
        salary.setEmpSalary(8000);

        List<Salary> salaryList = new ArrayList<>();
        salaryList.add(salary);

        // Add Salary list to employee object
        employee.setSalaryList(salaryList);

        EmployeeDAO empDao = new EmployeeDAO();

        // Create a new employee record in the database
        empDao.createEmployee(employee);

        // Update an existing employee's information
        employee.setLastName("UpdatedLastName");
        empDao.updateEmployee(employee);
    }

    /**
     * Method to insert information into the Employee table
     */
    public void createEmployee(Employee employee) {
        System.out.println(employee.toString()); // Print employee details
        try {
            // SQL query to insert information into table
            String query1 = "INSERT INTO Employee (employee_id, first_name, last_name, ssn, job_title, department) VALUES(?, ?, ?, ?, ?, ?)";
            // Get database connection
            con = getConnection();
            // Prepare Statement
            PreparedStatement myStmt = con.prepareStatement(query1);
            // Set Parameters
            myStmt.setInt(1, employee.getEmployeeid());
            myStmt.setString(2, employee.getFirstName());
            myStmt.setString(3, employee.getLastName());
            myStmt.setString(4, employee.getSsn());
            myStmt.setString(5, employee.getJobTitle());
            myStmt.setString(6, employee.getDepartment());
            // Execute SQL query
            myStmt.executeUpdate();

            setSalary(employee.getSalaryList().get(0));

        } catch (Exception e) {
            // Print in case of error
            e.printStackTrace();
        }
    }

    /**
     * Method to insert information into the Salary table
     */
    public void setSalary(Salary salary) {
        System.out.println(salary.toString()); // Print salary details
        try {
            // SQL query to insert information into table
            String query2 = "INSERT INTO Salary (salary_id, employee_id, emp_salary, salary_date) VALUES(?, ?, ?, ?)";
            // Get database connection
            con = getConnection();
            // Prepare Statement
            PreparedStatement myStmt = con.prepareStatement(query2);
            // Set Parameters
            myStmt.setInt(1, salary.getSalaryid());
            myStmt.setInt(2, salary.getEmployeeid());
            myStmt.setDouble(3, salary.getEmpsalary());
            // Convert Java Util date to SQL Date
            myStmt.setDate(4, new java.sql.Date(salary.getSalarydate().getTime()));
            // Execute SQL query
            myStmt.executeUpdate();
        } catch (Exception e) {
            // Print in case of error
            e.printStackTrace();
        }
    }

    /**
     * Method to update an employee's details and associated salary records
     * @throws Exception 
     */
    public boolean updateEmployee(Employee employee) throws Exception {
        String employeeQuery = "UPDATE Employee SET first_name = ?, last_name = ?, ssn = ?, job_title = ?, department = ? WHERE employee_id = ?";
        String salaryQuery = "UPDATE Salary SET emp_salary = ?, salary_date = ? WHERE salary_id = ? AND employee_id = ?";
        boolean success = false;

        try {
        	con = getConnection(); 
            con.setAutoCommit(false); // Start transaction

            // Update employee details
            try (PreparedStatement empStmt = con.prepareStatement(employeeQuery)) {
                empStmt.setString(1, employee.getFirstName());
                empStmt.setString(2, employee.getLastName());
                empStmt.setString(3, employee.getSsn());
                empStmt.setString(4, employee.getJobTitle());
                empStmt.setString(5, employee.getDepartment());
                empStmt.setInt(6, employee.getEmployeeid());
                empStmt.executeUpdate();
            }

            // Update associated salary records (if any)
            if (employee.getSalaryList() != null && !employee.getSalaryList().isEmpty()) {
                try (PreparedStatement salStmt = con.prepareStatement(salaryQuery)) {
                    for (Salary salary : employee.getSalaryList()) {
                        salStmt.setDouble(1, salary.getEmpsalary());
                        salStmt.setDate(2, new java.sql.Date(salary.getSalarydate().getTime()));
                        salStmt.setInt(3, salary.getSalaryid());
                        salStmt.setInt(4, employee.getEmployeeid());
                        salStmt.addBatch();
                    }
                    salStmt.executeBatch();
                }
            }

            con.commit(); // Commit transaction
            success = true;
            System.out.println("Employee and salary records updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Update failed. Rolling back transaction.");
            try {
                if (con != null) con.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }

        return success;
    }
}

