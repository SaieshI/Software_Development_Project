package com.employee.app.service;

import com.employee.model.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private List<Employee> employees = new ArrayList<>();

    // Add Employee
    public void addEmployee(Employee employee) {
        employees.add(employee);
        System.out.println("Employee added: " + employee);
    }

    // List All Employees
    public List<Employee> getAllEmployees() {
        return employees;
    }
}
