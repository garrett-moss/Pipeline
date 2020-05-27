package com.bank.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerLoginController {

	public static String login(HttpServletRequest request, HttpServletResponse response) {
		
		if(!request.getMethod().equals("POST")) {
			return "/login.html";
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(username.equals("apples") && password.equals("louise")) {
			System.out.println("Login Worked");
			
			request.getSession().setAttribute("loggedusername", username);
			request.getSession().setAttribute("loggedpassword", password);
			
			return "/api/CustomerHome";
		} else {
			return "/login.html";
		}
	}

}
