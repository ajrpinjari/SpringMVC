package com.jbk.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping(value = "/")
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value = "/homePage")
	public String homePage(HttpSession session, Model model) {
		String status = (String) session.getAttribute("status");
		if (status != null) {
			return "home";
		} else {
			model.addAttribute("msg", "Please Login First !!");
			return "login";
		}
	}

	@RequestMapping(value = "/addEmployeePage")
	public String addEmployeePage(HttpSession session, Model model) {
		String status = (String) session.getAttribute("status");
		if (status != null) {
			return "addEmployee";
		} else {
			model.addAttribute("msg", "Please Login First !!");
			return "login";
		}
	}
	
	@RequestMapping(value = "/importEmployee")
	public String importEmployee(HttpSession session, Model model) {
		String status = (String) session.getAttribute("status");
		if (status != null) {
			return "importEmployee";
		} else {
			model.addAttribute("msg", "Please Login First !!");
			return "login";
		}
	}
	
	//importEmployee
	

	// add student
	// /add-studentPage

}
