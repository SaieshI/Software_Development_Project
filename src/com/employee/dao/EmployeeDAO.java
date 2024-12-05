package com.employee.dao;

import com.employee.model.Employee;
import com.employee.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {

    /**
     * Add a new employee to the database.
     */
    public void addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO Employee (first_name, last_name, ssn, job_title, department) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Split the full name into first and last name
            String[] names = employee.getName().split(" ", 2);
            stmt.setString(1, names[0]); // First name
            stmt.setString(2, names.length > 1 ? names[1] : ""); // Last name
            stmt.setString(3, employee.getSsn());
            stmt.setString(4, employee.getJobTitle());
            stmt.setString(5, "General"); // Example department

            stmt.executeUpdate();
        }
    }

    /**
     * Search for an employee by SSN.
     */
    public Employee searchEmployee(String ssn) throws SQLException {
        String query = "SELECT * FROM Employee WHERE ssn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ssn);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve details from the ResultSet
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String jobTitle = rs.getString("job_title");
                    String department = rs.getString("department");
                    return new Employee(firstName + " " + lastName, ssn, jobTitle, 0); // Adjust as needed
                }
            }
        }
        return null; // Return null if no employee is found
    }

    /**
     * Update an employee's job title and salary by SSN.
     */
    public void updateEmployee(String ssn, String jobTitle, double salary) throws SQLException {
        String query = "UPDATE Employee SET job_title = ?, salary = ? WHERE ssn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, jobTitle);
            stmt.setDouble(2, salary);
            stmt.setString(3, ssn);

            stmt.executeUpdate();
        }
    }

    /**
     * Delete an employee from the database by SSN.
     */
    public void deleteEmployee(String ssn) throws SQLException {
        String query = "DELETE FROM Employee WHERE ssn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ssn);

            stmt.executeUpdate();
        }
    }
}
