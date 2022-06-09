package com.jbk.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.jbk.entity.Employee;


public interface EmployeeDao {
	
	public Employee login(Employee employee);

	
	public Employee saveEmployee(Employee Employee);
	public Employee getEmployeeById(String EmployeeId);
	public List<Employee> getAllEmployee();
	public boolean deleteEmployeeById(String EmployeeId);
	public Employee updateEmployee(Employee Employee);
	public void logout(String employeeId);
	public String saveEmployees(List<Employee> list);



}
