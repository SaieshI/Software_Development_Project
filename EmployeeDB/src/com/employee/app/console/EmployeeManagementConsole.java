package com.employee.app.console;

import com.employee.model.Employee;
import com.employee.model.EmployeeDAO;
import com.employee.model.FullTimeEmployeeReportGenerator;
import com.employee.model.Salary;
import com.employee.model.TotalPayByDepartmentReport;
import com.employee.model.TotalPayByJobTitleReport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagementConsole {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EmployeeDAO employeeDAO = new EmployeeDAO();

    public void run() {
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> updateEmployee();
                case 3 -> deleteEmployee();
                case 4 -> searchEmployee();
                case 5 -> generateReports();
                case 0 -> exitApplication();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n--- Employee Management System ---");
        System.out.println("1. Add Employee");
        System.out.println("2. Update Employee");
        System.out.println("3. Delete Employee");
        System.out.println("4. Search Employee");
        System.out.println("5. Generate Reports");
        System.out.println("0. Exit");
    }

    private static void addEmployee() {
        System.out.println("\n--- Add Employee ---");
        Employee employee = new Employee();
        employee.setEmployeeid(getIntInput("Enter Employee ID: "));
        employee.setFirstName(getStringInput("Enter First Name: "));
        employee.setLastName(getStringInput("Enter Last Name: "));
        employee.setSsn(getStringInput("Enter SSN: "));
        employee.setJobTitle(getStringInput("Enter Job Title: "));
        employee.setDepartment(getStringInput("Enter Department: "));

        Salary salary = new Salary();
        salary.setSalaryid(getIntInput("Enter Salary ID: "));
        salary.setEmployeeid(employee.getEmployeeid());
        salary.setEmpSalary(getDoubleInput("Enter Salary Amount: "));
        salary.setSalarydate(new Date());

        List<Salary> salaryList = new ArrayList<>();
        salaryList.add(salary);
        employee.setSalaryList(salaryList);

        employeeDAO.createEmployee(employee);
        System.out.println("Employee added successfully.");
    }

    private static void updateEmployee() {
        System.out.println("\n--- Update Employee ---");
        Employee employee = new Employee();
        employee.setEmployeeid(getIntInput("Enter Employee ID to update: "));
        employee.setFirstName(getStringInput("Enter Updated First Name: "));
        employee.setLastName(getStringInput("Enter Updated Last Name: "));
        employee.setSsn(getStringInput("Enter Updated SSN: "));
        employee.setJobTitle(getStringInput("Enter Updated Job Title: "));
        employee.setDepartment(getStringInput("Enter Updated Department: "));

        try {
            boolean success = employeeDAO.updateEmployee(employee);
            if (success) {
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Failed to update employee.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteEmployee() {
        System.out.println("\n--- Delete Employee ---");
        int employeeId = getIntInput("Enter Employee ID to delete: ");
        try {
            boolean success = employeeDAO.deleteEmployee(employeeId);
            if (success) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee not found or failed to delete.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void searchEmployee() {
        System.out.println("\n--- Search Employee ---");
        int employeeId = getIntInput("Enter Employee ID to search: ");
        try {
            Employee employee = employeeDAO.getEmployeeById(employeeId);
            if (employee != null) {
                System.out.println("Employee Details:");
                System.out.println("ID: " + employee.getEmployeeid());
                System.out.println("Name: " + employee.getFirstName() + " " + employee.getLastName());
                System.out.println("SSN: " + employee.getSsn());
                System.out.println("Job Title: " + employee.getJobTitle());
                System.out.println("Department: " + employee.getDepartment());
                System.out.println("Salaries:");
                for (Salary salary : employee.getSalaryList()) {
                    System.out.println(" - Salary ID: " + salary.getSalaryid());
                    System.out.println("   Amount: " + salary.getEmpsalary());
                    System.out.println("   Date: " + salary.getSalarydate());
                }
            } else {
                System.out.println("No employee found with ID: " + employeeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    private static void generateReports() {
        System.out.println("\n--- Generate Reports ---");
        System.out.println("1. Total Pay by Department");
        System.out.println("2. Total Pay by Job Title");
        System.out.println("3. Full-Time Employee Report");
        int reportChoice = getIntInput("Enter your choice: ");
    
        switch (reportChoice) {
            case 1 -> generateTotalPayByDepartment();
            case 2 -> generateTotalPayByJobTitle();
            case 3 -> generateFullTimeEmployeeReport();
            default -> System.out.println("Invalid choice. Returning to main menu.");
        }
    }
    
    private static void generateTotalPayByDepartment() {
        System.out.println("\n--- Total Pay by Department ---");
        TotalPayByDepartmentReport.generateTotalPayByDepartmentReport();
    }

    private static void generateTotalPayByJobTitle() {
        System.out.println("\n--- Total Pay by Job Title ---");
        TotalPayByJobTitleReport report = new TotalPayByJobTitleReport();
        try {
            report.generatePayByJobTitle();
        } catch (Exception e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }

    private static void generateFullTimeEmployeeReport() {
        System.out.println("\n--- Full-Time Employee Report ---");
        FullTimeEmployeeReportGenerator report = new FullTimeEmployeeReportGenerator();
        report.generateFullTimeEmployeeReport();
    }
    

    private static void exitApplication() {
        System.out.println("Exiting the application. Goodbye!");
        System.exit(0);
    }

    // Utility methods for input
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
