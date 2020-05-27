package com.bank.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bank.dao.impl.EmployeeDaoImpl;
import com.bank.exception.BankException;
import com.bank.model.Employee;
import com.bank.service.impl.EmployeeServiceImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeLoginController {
public static String login(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		
		if(!request.getMethod().equals("POST")) {
			return "/employeeLogin.html";
		}
		
		EmployeeServiceImpl service = new EmployeeServiceImpl();
		EmployeeDaoImpl dao = new EmployeeDaoImpl();
		ObjectMapper mapper = new ObjectMapper();
		
		Employee localEmployee = mapper.readValue(request.getReader(), Employee.class);
		
		String localUsername = localEmployee.getUsername();
		String localPassword = localEmployee.getPassword();
		
		try {
			Employee dbEmployee = dao.loginVerification(localUsername);
			String dbUsername = dbEmployee.getUsername();
			String dbPassword = dbEmployee.getPassword();
			if (dbUsername.equals(localUsername) && dbPassword.equals(localPassword)) {
				request.getSession().setAttribute("loggedusername", dbUsername);
				request.getSession().setAttribute("loggedpassword", dbPassword);
				return "Employee.html";
			} else {
				return "employeeLogin.html";
			}
		} catch (BankException e) {
			e.printStackTrace();
		}
		return null;
}}

		
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//		
//		if(username.equals("apples") && password.equals("louise")) {
//			System.out.println("Login Worked");
//			
//			request.getSession().setAttribute("loggedusername", username);
//			request.getSession().setAttribute("loggedpassword", password);
//			
//			return "/api/EmployeeHome";
//		} else {
//			return "/employeeLogin.html";
//		}
