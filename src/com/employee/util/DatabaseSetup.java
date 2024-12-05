package com.employee.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = "Incursion*73"; // Your MySQL password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            // Step 1: Create Database
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS EmployeeManagementDB";
            stmt.executeUpdate(createDatabaseSQL);
            System.out.println("Database 'EmployeeManagementDB' created or already exists.");

            // Step 2: Switch to Database
            String useDatabaseSQL = "USE EmployeeManagementDB";
            stmt.executeUpdate(useDatabaseSQL);
            System.out.println("Using database 'EmployeeManagementDB'.");

            // Step 3: Create Employee Table
            String createEmployeeTableSQL = """
                CREATE TABLE IF NOT EXISTS Employee (
                    employee_id INT PRIMARY KEY AUTO_INCREMENT,
                    first_name VARCHAR(100),
                    last_name VARCHAR(100),
                    ssn VARCHAR(11),
                    job_title VARCHAR(100),
                    department VARCHAR(100)
                )
            """;
            stmt.executeUpdate(createEmployeeTableSQL);
            System.out.println("Table 'Employee' created or already exists.");

            // Step 4: Create Salary Table
            String createSalaryTableSQL = """
                CREATE TABLE IF NOT EXISTS Salary (
                    salary_id INT PRIMARY KEY AUTO_INCREMENT,
                    employee_id INT,
                    emp_salary DOUBLE(10,2),
                    salary_date DATE,
                    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
                )
            """;
            stmt.executeUpdate(createSalaryTableSQL);
            System.out.println("Table 'Salary' created or already exists.");

        } catch (SQLException e) {
            System.err.println("Database setup failed: " + e.getMessage());
        }
    }
}
