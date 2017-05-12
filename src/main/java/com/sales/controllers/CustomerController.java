package com.sales.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sales.models.Customer;
import com.sales.services.CustomerService;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/showCustomers")
	public void showCustomers(Model m) {
		
		ArrayList<Customer> customers = customerService.getCustomerList();
		
		m.addAttribute("customers", customers);
	}
	
	@RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
	public String getCustomer(@ModelAttribute ("newCustomer") Customer customer) {
		return "addCustomer";
	}
	
	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
	public String postCustomer(@Valid @ModelAttribute("newCustomer") Customer customer, BindingResult res, Model m) {
		
		try {
			customerService.addCustomer(customer);
			
			return "redirect:showCustomers";
		} catch (Exception e) {
			System.out.println("Invalid Form Submission");
			//e.printStackTrace();
			return null;
		}
		
	}
}
