package com.employee.model;
// Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalPayByDepartmentReport {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database"; // Replace 'your_database'
    private static final String DB_USER = "your_username"; // Replace 'your_username'
    private static final String DB_PASSWORD = "your_password"; // Replace 'your_password'

    public static void main(String[] args) {
        generateTotalPayByDepartmentReport();
    }

    public static void generateTotalPayByDepartmentReport() {
        String query = "SELECT department, SUM(salary) AS total_pay " +
                       "FROM employees " +
                       "GROUP BY department";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("--- Total Pay by Department ---");
            System.out.printf("%-20s %-10s\n", "Department", "Total Pay");
            System.out.println("----------------------------------");

            // Iterate through the result set and print each department's total pay
            while (resultSet.next()) {
                String department = resultSet.getString("department");
                double totalPay = resultSet.getDouble("total_pay");
                System.out.printf("%-20s $%-10.2f\n", department, totalPay);
            }

        } catch (SQLException e) {
            System.err.println("An error occurred while generating the report: " + e.getMessage());
        }
    }
}
