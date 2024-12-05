package com.employee.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

public class FullTimeEmployeeReportGenerator {

    public void generateFullTimeEmployeeReport() {
        String query = "SELECT e.emp_id, e.name, e.job_title, e.salary, ps.statement_date, ps.amount " +
                       "FROM employees e " +
                       "JOIN pay_statements ps ON e.emp_id = ps.emp_id " +
                       "WHERE e.employment_type = 'Full-Time' " +
                       "ORDER BY e.emp_id, ps.statement_date";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company2", "username", "password");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Full-Time Employee Information with Pay Statement History:");
            System.out.println("Emp ID | Name | Job Title | Salary | Statement Date | Amount");
            System.out.println("------------------------------------------------------------");

            while (resultSet.next()) {
                int empId = resultSet.getInt("emp_id");
                String name = resultSet.getString("name");
                String jobTitle = resultSet.getString("job_title");
                double salary = resultSet.getDouble("salary");
                Date statementDate = resultSet.getDate("statement_date");
                double amount = resultSet.getDouble("amount");

                System.out.println(empId + " | " + name + " | " + jobTitle + " | $" + salary + " | " + statementDate + " | $" + amount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
