package EmployeeDB.src.com.employee.model;

import java.util.List;

public class Employee {
	@Override
	public String toString() {
		return "Employee [employeeid=" + employeeid + ", firstName=" + firstName + ", lastName=" + lastName + ", ssn="
				+ ssn + ", jobTitle=" + jobTitle + ", department=" + department + "]";
	}
	private int employeeid;
	private String firstName;
	private String lastName;
	private String ssn;
	private String jobTitle;
	private String department;
	private List<Salary> salaryList;
	
	public List<Salary> getSalaryList() {
		return salaryList;
	}
	public void setSalaryList(List<Salary> salaryList) {
		this.salaryList = salaryList;
	}
	public int getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	

}
