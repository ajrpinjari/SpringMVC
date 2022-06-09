package com.jbk.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jbk.entity.Employee;
import com.jbk.service.EmployeeService;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService service;

	@PostMapping(value = "/loginProcess")
	public ModelAndView loginProcess(@ModelAttribute Employee employee, HttpSession session) {
		System.out.println(employee);
		Employee emp = service.login(employee);
		System.out.println(emp);
		if (emp != null) {
			session.setAttribute("username", emp.getUsername());
			session.setAttribute("eid", emp.getEmployeeId());
			session.setAttribute("role", emp.getRole());
			session.setAttribute("status", emp.getStatus());
			return new ModelAndView("home");
		} else {
			// model.addAttribute("msg", "Invalid Credentials !!");
			return new ModelAndView("login", "msg", "Invalid Credentials !!");
		}

	}

	@PostMapping(value = "/saveEmployee")
	public ModelAndView saveEmployee(@ModelAttribute Employee employee) {

		System.out.println("in save");
		Employee emp = service.saveEmployee(employee);
		if (emp != null) {
			return new ModelAndView("addEmployee", "msg", "Saved Successfully !!");
		} else {
			return new ModelAndView("addEmployee", "msg", "NOt Saved.. Something Wrong !!");

		}

	}

	@GetMapping(value = "/getEmployee")
	public ModelAndView getEmployee(@RequestParam String eid, @RequestParam String msg, Model model) {

		Employee employee = service.getEmployeeById(eid);
		if (!msg.equalsIgnoreCase("null")) {
			model.addAttribute("msg", msg);
		}
		model.addAttribute("employee", employee);

		return new ModelAndView("profile");

	}

	@GetMapping(value = "/getAllEmployee")
	public ModelAndView getAllEmployee() {
		List<Employee> list = service.getAllEmployee();

		return new ModelAndView("listOfEmployee", "employees", list);

	}

	@GetMapping(value = "/deleteEmployeeById")
	public String deleteEmployeeById(@RequestParam String eid) {
		boolean b = service.deleteEmployeeById(eid);
		System.out.println(b);
		if (b) {
			return "redirect:/getAllEmployee";
		} else {
			return "redirect:/getAllEmployee";

		}

	}

	@PostMapping(value = "/updateEmployee")
	public ModelAndView updateEmployee(@ModelAttribute Employee employee) {
		Employee emp = service.updateEmployee(employee);
		if (emp != null) {
			return new ModelAndView("redirect:/getEmployee?eid=" + employee.getEmployeeId() + "&msg=Updated");
		} else {
			return new ModelAndView("redirect:/getEmployee?eid=" + employee.getEmployeeId() + "&msg=Not Updated");
		}

	}

	@RequestMapping(value = "/logout")
	public String logout(@RequestParam String eid, HttpSession session) {
		service.logout(eid, session);
		return "login";
	}

	@PostMapping(value = "/uploadsheet")
	public ModelAndView uploadsheet(@RequestParam CommonsMultipartFile file, HttpSession session) {
		System.out.println("in file");
		String msg = service.uploadsheet(file, session);
		return new ModelAndView("importEmployee","msg",msg);

	}

	// uploadsheet

}
