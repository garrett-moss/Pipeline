package com.bank.controller;

import javax.servlet.http.HttpServletRequest;
import com.bank.service.impl.CustomerServiceImpl;
import javax.servlet.http.HttpServletResponse;
import com.bank.model.Customer;

public class CustomerHomeController {
	
	
	public static String home(HttpServletRequest request, HttpServletResponse response) {
		
		return "/Customer.html";
	}
}
