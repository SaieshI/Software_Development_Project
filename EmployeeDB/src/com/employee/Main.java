package com.employee;

import com.employee.app.console.EmployeeManagementConsole;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Employee Management System...");
        EmployeeManagementConsole console = new EmployeeManagementConsole();
        console.run(); // Delegates execution to the console-based system
    }
}
