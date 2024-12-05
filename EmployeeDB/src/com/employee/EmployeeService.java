package com.employee;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.employee.model.EmployeeDAO;
public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    public EmployeeService() {
        employeeDAO = new EmployeeDAO();
    }
    public void updateEmployeeSalary(double percentage, double minSalary, double maxSalary) throws Exception {
        String query = "UPDATE Salary SET emp_salary = emp_salary + (emp_salary * ? / 100) " +
        "WHERE emp_salary BETWEEN ? AND ?";
        try (Connection con = employeeDAO.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setDouble(1, percentage);
            stmt.setDouble(2, minSalary);
            stmt.setDouble(3, maxSalary);
            int rowsUpdated = stmt.executeUpdate();
            System.out.println(rowsUpdated + " rows updated.");
        }
    }
}