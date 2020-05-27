package com.bank.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bank.dao.impl.EmployeeDaoImpl;
import com.bank.exception.BankException;
import com.bank.model.Employee;
import com.bank.service.impl.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/employeeLogin")
public class EmployeeLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Something happened");
				doPost(request,response);
}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost"); 
		  response.setContentType("application/json");
		 
		 		 EmployeeServiceImpl esi = new EmployeeServiceImpl();
		 EmployeeDaoImpl edi = new EmployeeDaoImpl();
		 ObjectMapper mapper = new ObjectMapper();
		 PrintWriter writer = response.getWriter();
		 
		 Employee localEmployee = mapper.readValue(request.getReader(), com.bank.model.Employee.class);
		 
		 String localUsername = localEmployee.getUsername();
		 String localPassword = localEmployee.getPassword();
		 
		
		 
		 try {
			 
			 Employee dbEmployee = edi.loginVerification(localUsername);
			 
			 String dbUsername = dbEmployee.getUsername();
			 String dbPassword = dbEmployee.getPassword();
			 
			 if(dbUsername.equals(localUsername)&& dbPassword.equals(localPassword)) {
				 
				 //writer.write("Employee.html");
				 System.out.println("Customer successfully logged in: ");
				 writer.write("Employee.html");
			 } else {
				 System.out.println("Credentials DO NOT match DB!");
				 writer.write("employeeLogin.html");
			 } 
		 }catch(BankException e) {
				 e.printStackTrace();
			 }
		 }
}
		 
//	        PrintWriter out=response.getWriter();  
//	          
//	        String username=request.getParameter("exampleInputEmail1");  
//	        String password=request.getParameter("exampleInputPassword1");  
//	        
//	        if(dao.loginVerification(username)){  
//	        	RequestDispatcher rd=request.getRequestDispatcher("servlet2");  
//	            rd.forward(request,response);   
//	        }  
//	        else{  
//	            out.print("Sorry, username or password error!");  
//	            request.getRequestDispatcher("login.html").include(request, response);  
//	        }  
//	        out.close();   
	
