package EmployeeDB.src.com.employee.model;

import java.sql.*;
import java.util.List;

public class updateEmployee {

    public boolean updateEmployeeDetails(Employee employee, Connection con) throws SQLException {
        String employeeQuery = "UPDATE Employee SET first_name = ?, last_name = ?, ssn = ?, " +
                               "job_title = ?, department = ? WHERE employee_id = ?";
        String salaryQuery = "UPDATE Salary SET emp_salary = ?, salary_date = ? " +
                             "WHERE salary_id = ? AND employee_id = ?";
        boolean success = false;

        try {
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
            con.rollback(); // Roll back transaction on failure
            System.err.println("Update failed. Transaction rolled back.");
            throw e; // Re-throw exception to propagate it
        }

        return success;
    }
}
