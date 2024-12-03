package EmployeeDB.src.com.employee.model;

import java.util.Date;

public class Salary{ 
	@Override
	public String toString() { 
		return "Salary [salaryid =" + salaryid + ", employeeid =" + employeeid + "empsalary =" + empSalary + "salarydate =" + salarydate + "]"; 
	}
	private int salaryid; 
	private int employeeid; 
	private double empSalary; 
	private Date salarydate;
	
	public int getSalaryid() {
		return salaryid;
	}
	public void setSalaryid(int salaryid) {
		this.salaryid = salaryid;
	}
	public int getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}
	public double getEmpsalary() {
		return empSalary;
	}
	public void setEmpSalary(double empsalary) {
		this.empSalary = empsalary;
	}
	public Date getSalarydate() {
		return salarydate;
	}
	public void setSalarydate(Date salarydate) {
		this.salarydate = salarydate;
	} 
	
}