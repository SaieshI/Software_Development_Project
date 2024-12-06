package com.employee.dao;

import com.employee.model.Employee;
import com.employee.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class EmployeeDAO {

    /**
     * Add a new employee to the database.
     */
    public void addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO Employee (first_name, last_name, ssn_no_dashes, job_title, department, salary) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            String[] names = employee.getName().split(" ", 2);
            stmt.setString(1, names[0]); // First name
            stmt.setString(2, names.length > 1 ? names[1] : ""); // Last name
            stmt.setString(3, employee.getSsn());
            stmt.setString(4, employee.getJobTitle());
            stmt.setString(5, employee.getDepartment());
            stmt.setBigDecimal(6, BigDecimal.valueOf(employee.getSalary())); // Set salary
    
            stmt.executeUpdate();
        }
    }
    
    /**
     * Search for an employee by SSN, Name, or ID.
     */
    public Employee searchEmployee(String identifier) throws SQLException {
        String query = "SELECT * FROM Employee WHERE ssn_no_dashes = ? OR CONCAT(first_name, ' ', last_name) = ? OR employee_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, identifier);
            stmt.setString(2, identifier);
            stmt.setString(3, identifier);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String jobTitle = rs.getString("job_title");
                    String department = rs.getString("department");
                    return new Employee(firstName + " " + lastName, rs.getString("ssn_no_dashes"), jobTitle, 0, department);
                }
            }
        }
        return null;
    }

    /**
     * Update an employee's job title and salary by SSN.
     */
    public boolean updateEmployee(String ssn, String jobTitle, double salary, String department) throws SQLException {
        String updateEmployeeQuery = "UPDATE Employee SET job_title = ?, department = ? WHERE ssn_no_dashes = ?";
        String updateSalaryQuery = "UPDATE Salary SET emp_salary = ? WHERE employee_id = (SELECT employee_id FROM Employee WHERE ssn_no_dashes = ?)";
        boolean updated = false;
    
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Update job title and department in the Employee table
            try (PreparedStatement stmt = conn.prepareStatement(updateEmployeeQuery)) {
                stmt.setString(1, jobTitle);
                stmt.setString(2, department);
                stmt.setString(3, ssn);
                int rowsAffected = stmt.executeUpdate();
                updated = rowsAffected > 0; // Check if any rows were updated
            }
    
            // Update salary in the Salary table
            try (PreparedStatement stmt = conn.prepareStatement(updateSalaryQuery)) {
                stmt.setDouble(1, salary);
                stmt.setString(2, ssn);
                stmt.executeUpdate();
            }
        }
        return updated;
    }
    
    
    
    /**
     * Delete an employee from the database by SSN.
     */
    public void deleteEmployee(String ssn) throws SQLException {
        String query = "DELETE FROM Employee WHERE ssn_no_dashes = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ssn);

            stmt.executeUpdate();
        }
    }

    /**
     * Update salaries within a specific range by a percentage.
     */
    public void updateSalaryByCondition(double percentage, double minSalary, double maxSalary) throws SQLException {
        String query = "UPDATE Salary SET emp_salary = emp_salary * (1 + ? / 100) WHERE emp_salary >= ? AND emp_salary < ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, percentage);
            stmt.setDouble(2, minSalary);
            stmt.setDouble(3, maxSalary);

            stmt.executeUpdate();
        }
    }

/**
 * Generate a report for total pay grouped by Job Title.
 */
public Map<String, Double> getTotalPayByJobTitle() throws SQLException {
    String query = "SELECT job_title, SUM(salary) AS total_pay FROM Employee GROUP BY job_title ORDER BY job_title";
    Map<String, Double> report = new LinkedHashMap<>();

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            report.put(rs.getString("job_title"), rs.getDouble("total_pay"));
        }
    }
    return report;
}

/**
 * Generate a report for total pay grouped by Department.
 */
public Map<String, Double> getTotalPayByDepartment() throws SQLException {
    String query = "SELECT department, SUM(salary) AS total_pay FROM Employee GROUP BY department ORDER BY department";
    Map<String, Double> report = new LinkedHashMap<>();

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            report.put(rs.getString("department"), rs.getDouble("total_pay"));
        }
    }
    return report;
}

    /**
     * Retrieve all employees for displaying in the GUI.
     */
    public List<Employee> getAllEmployees() throws SQLException {
        String query = "SELECT first_name, last_name, ssn_no_dashes, job_title, department, salary FROM Employee";
        List<Employee> employees = new ArrayList<>();
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String ssn = rs.getString("ssn_no_dashes");
                String jobTitle = rs.getString("job_title");
                String department = rs.getString("department");
                double salary = rs.getDouble("salary");
    
                employees.add(new Employee(firstName + " " + lastName, ssn, jobTitle, salary, department));
            }
        }
        return employees;
    }
    
}
