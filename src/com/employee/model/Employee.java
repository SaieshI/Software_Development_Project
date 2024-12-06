package com.employee.model;

public class Employee {
    private String name;
    private String ssn;
    private String jobTitle;
    private double salary;
    private String department; // Add department field

    // Constructor with department
    public Employee(String name, String ssn, String jobTitle, double salary, String department) {
        this.name = name;
        this.ssn = ssn;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.department = department;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for SSN
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    // Getter and setter for job title
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    // Getter and setter for salary
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Getter and setter for department
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
