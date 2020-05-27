package com.bank.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.impl.CustomerDaoImpl;
import com.bank.exception.BankException;
import com.bank.model.Customer;
import com.bank.service.impl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		System.out.println("Something happened");
		
		PrintWriter pw = res.getWriter();
		pw.write("<h1> We're sending something back now, inside of doGet</h1>");
		
		doPost(req,res);
}
		
		protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		CustomerServiceImpl csi = new CustomerServiceImpl();
		CustomerDaoImpl cdi = new CustomerDaoImpl();
		ObjectMapper mapper = new ObjectMapper();
		Customer customer = mapper.readValue(req.getReader(), com.bank.model.Customer.class);
		
		System.out.println(customer);
		System.out.println("username = " + customer.getUsername());
	    System.out.println("password = " + customer.getPassword());
	    
	    String username = customer.getUsername();
		String password = customer.getPassword();
				
		res.setContentType("application/json");
		PrintWriter writer = res.getWriter();
		try {
			if (username.equals(csi.loginVerification(username).getUsername()) && password.equals(csi.loginVerification(username).getPassword())){
				System.out.println("login successful");
				writer.write("Customer.html");
			}else {
				writer.write("login.html");
			}
			}catch(BankException e) {
				e.getStackTrace();
			}
		}
}
