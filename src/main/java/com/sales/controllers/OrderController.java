package com.sales.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sales.exceptions.CustomerQuantityException;
import com.sales.exceptions.InvalidCustomerException;
import com.sales.exceptions.InvalidProductException;
import com.sales.exceptions.ProductCustomerException;
import com.sales.exceptions.QuantityTooLargeException;
import com.sales.models.Customer;
import com.sales.models.Order;
import com.sales.models.Product;
import com.sales.repositories.CustomerRepository;
import com.sales.repositories.ProductRepository;
import com.sales.services.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@RequestMapping(value = "/showOrders")
	public void showOrders(Model m) {
		ArrayList<Order> orders = orderService.getOrderList();
		
		m.addAttribute("orders", orders);
	}
	
	@RequestMapping(value = "/addOrder", method = RequestMethod.GET)
	public String getOrder(@ModelAttribute ("newOrder") Order order) {
		return "addOrder";
	}
	
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public String postOrders(@Valid @ModelAttribute("newOrder") Order order, BindingResult res, Model m) throws Exception {
		
		Product product = productRepository.findOne(order.getProd().getpId());
		Customer customer = customerRepository.findOne(order.getCust().getcId());
		
		if(product == null && customer == null) {
			throw new ProductCustomerException("No such product: " + order.getProd().getpId() + " No such customer: " + order.getCust().getcId(), order);
		} else if(customer == null && product.getQtyInStock() < order.getQty()) {
			throw new CustomerQuantityException("Quantity too large: Product stock = " + product.getQtyInStock() + " No such customer: " + order.getCust().getcId(), order);
		} else if (product == null) {
			throw new InvalidProductException("No such product: " + order.getProd().getpId(), order);
		} else if (customer == null) {
			throw new InvalidCustomerException("No such customer: " + order.getCust().getcId(), order);
		} else if (product.getQtyInStock() < order.getQty()) {
			throw new QuantityTooLargeException("Quantity too large: Product stock = " + product.getQtyInStock(), order);
		}
		
		try {
			orderService.addOrder(order);
			
			return "redirect:showOrders";	
		} catch (Exception e) {
			return null;
		}		
	}
	
	@ExceptionHandler(ProductCustomerException.class)
	public String orderErrorHandler(Model m, ProductCustomerException e) {
		Order order = e.getOrder();
		m.addAttribute("errMessage", e.getMessage());
		m.addAttribute(order);
		
		return "orderError";
	}
	
	@ExceptionHandler(CustomerQuantityException.class)
	public String orderErrorHandler(Model m, CustomerQuantityException e) {
		Order order = e.getOrder();
		m.addAttribute("errMessage", e.getMessage());
		m.addAttribute(order);
		
		return "orderError";
	}
	
	@ExceptionHandler(InvalidProductException.class)
	public String orderErrorHandler(Model m, InvalidProductException e) {	
		Order order = e.getOrder();
		m.addAttribute("errMessage", e.getMessage());
		m.addAttribute(order);
				
		return "orderError";
	}
	
	@ExceptionHandler(InvalidCustomerException.class)
	public String orderErrorHandler(Model m, InvalidCustomerException e) {
		Order order = e.getOrder();
		m.addAttribute("errMessage", e.getMessage());
		m.addAttribute(order);
		
		return "orderError";
	}
	
	@ExceptionHandler(QuantityTooLargeException.class)
	public String orderErrorHandler(Model m, QuantityTooLargeException e) {
		Order order = e.getOrder();
		m.addAttribute("errMessage", e.getMessage());
		m.addAttribute(order);
		
		return "orderError";
	}
}
