package com.employee.model;

public class Employee {
    private String name;
    private String ssn;
    private String jobTitle;
    private double salary;

    public Employee(String name, String ssn, String jobTitle, double salary) {
        this.name = name;
        this.ssn = ssn;
        this.jobTitle = jobTitle;
        this.salary = salary;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
