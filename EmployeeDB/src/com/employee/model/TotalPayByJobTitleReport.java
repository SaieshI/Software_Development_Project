package EmployeeDB.src.com.employee.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TotalPayByJobTitleReport {
    private final EmployeeDAO employeeDAO;
    public TotalPayByJobTitleReport() {
        employeeDAO = new EmployeeDAO();
    }
    public void generatePayByJobTitle() throws Exception {
        String query = "SELECT e.job_title, SUM(s.emp_salary) AS total_pay " +
        "FROM Employee e " +
        "JOIN Salary s ON e.employee_id = s.employee_id " +
        "GROUP BY e.job_title";
        try (Connection con = employeeDAO.getConnection();
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {
            System.out.println("Total Pay by Job Title:");
            while (rs.next()) {
                System.out.println("Job Title: " + rs.getString("job_title") +
                ", Total Pay: " + rs.getDouble("total_pay"));
                }
        }
    }
}