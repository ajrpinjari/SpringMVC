package com.jbk.dao;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.entity.Employee;

@Repository
public class EmployeeDao_impl implements EmployeeDao {

	@Autowired
	private SessionFactory sf;

	@Override
	public Employee saveEmployee(Employee Employee) {

		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(Employee);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}

		return Employee;
	}

	@Override
	public Employee getEmployeeById(String EmployeeId) {
		Session session = sf.openSession();
		Employee Employee = null;
		try {
			Employee = session.get(Employee.class, EmployeeId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return Employee;
	}

	@Override
	public List<Employee> getAllEmployee() {
		Session session = sf.openSession();
		List<Employee> list = null;
		try {
			Criteria criteria = session.createCriteria(Employee.class);
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public boolean deleteEmployeeById(String EmployeeId) {
		Session session = sf.openSession();

		Transaction transaction = session.beginTransaction();
		boolean b = false;
		try {
			Employee Employee = session.load(Employee.class, EmployeeId);
			if (Employee != null) {
				session.delete(Employee);
				transaction.commit();
				b = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		} finally {
			session.close();
		}
		return b;
	}

	@Override
	public Employee updateEmployee(Employee Employee) {
		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.update(Employee);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}

		return Employee;
	}

	@Override
	public Employee login(Employee employee) {

		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction();
		Employee emp = null;
		try {
			Criteria criteria = session.createCriteria(Employee.class);
			Criterion username = Restrictions.eq("username", employee.getUsername());
			Criterion password = Restrictions.eq("password", employee.getPassword());
			criteria.add(Restrictions.and(username, password));

			emp = (Employee) criteria.uniqueResult();
			emp.setStatus("active");
			session.update(emp);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return emp;

	}

	@Override
	public void logout(String employeeId) {
		Employee employee = getEmployeeById(employeeId);
		employee.setStatus("inactive");
		updateEmployee(employee);

	}

	@Override
	public String saveEmployees(List<Employee> list) {
		int counter=0;
		for (Employee employee : list) {
			Session session = sf.openSession();
			Transaction transaction = session.beginTransaction();
			try {
				session.save(employee);
				transaction.commit();
				counter=counter+1;
			} catch (Exception e) {
				e.printStackTrace();
				return "Something wrong while uploading sheet";
			} finally {
				session.close();
			}
		}

		return counter +"record inserted";
	}

}
