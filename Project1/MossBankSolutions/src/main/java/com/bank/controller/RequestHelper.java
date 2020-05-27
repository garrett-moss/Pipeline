package com.bank.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class RequestHelper {
	
	
	public static String process(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
	switch (request.getRequestURI()) {
	
	case "MossBankSolutions/api/CustomerHome":
		
		return CustomerHomeController.home(request, response);
		
	case "MossBankSolutions/api/EmployeeHome":
		
		return EmployeeHomeController.home(request, response);

	case "MossBankSolutions/api/CustomerLogin":
		
		return CustomerLoginController.login(request, response);
		
	case "MossBankSolutions/EmployeeLogin":
		
		return EmployeeLoginController.login(request, response);
		
	
	}
	
	return "login.html";
}
}
