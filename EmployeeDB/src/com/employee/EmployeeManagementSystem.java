package com.employee;

//required imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeManagementSystem {

    // Method to search for an employee based on name, SSN, empid
    public void searchEmployee(Connection connection, String criteria, String value) {
       //SQL query to search
        String query = "SELECT * FROM Employee WHERE " + criteria + " = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, value);  //set the value of the placeholder
            ResultSet rs = statement.executeQuery();  //execute and store results in ResultSet

            if (!rs.isBeforeFirst()) {     //check if empty
                System.out.println("No employee found with " + criteria + ": " + value);  //if not found
            } else {
                while (rs.next()) {
                    //print employee results
                    System.out.println("Employee ID: " + rs.getInt("empid"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Job Title: " + rs.getString("job_title"));
                    System.out.println("Department: " + rs.getString("department"));
                    System.out.println("Salary: $" + rs.getDouble("salary"));
                    System.out.println("SSN: " + rs.getString("SSN"));
                }
            }
        } catch (SQLException e) {
            //any SQL exceptions
            System.out.println("Error during search: " + e.getMessage());
        }
    }
}