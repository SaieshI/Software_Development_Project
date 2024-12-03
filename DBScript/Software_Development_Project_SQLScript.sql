CREATE table Employee (
employee_id int primary key, 
first_name varchar(100), 
last_name varchar(100), 
ssn varchar(11), 
job_title varchar(100), 
department varchar(100)
);

CREATE table Salary (
salary_id int primary key,
employee_id int, 
emp_salary double(10,2),
salary_date date, 
FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
)
