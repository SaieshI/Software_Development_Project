package com.employee.app.service;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    private EmployeeDAO employeeDAO;

    // Constructor
    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    // Add Employee
    public void addEmployee(Employee employee) throws SQLException {
        employeeDAO.addEmployee(employee);
        System.out.println("Employee added: " + employee);
    }

    // List All Employees
    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDAO.getAllEmployees();
    }

    // Update Employee
    public void updateEmployee(String ssn, String jobTitle, double salary, String department) throws SQLException {
        employeeDAO.updateEmployee(ssn, jobTitle, salary, department);
        System.out.println("Employee updated with SSN: " + ssn);
    }

    // Delete Employee
    public void deleteEmployee(String ssn) throws SQLException {
        employeeDAO.deleteEmployee(ssn);
        System.out.println("Employee deleted with SSN: " + ssn);
    }

    // Search Employee
    public Employee searchEmployee(String identifier) throws SQLException {
        return employeeDAO.searchEmployee(identifier);
    }
}
