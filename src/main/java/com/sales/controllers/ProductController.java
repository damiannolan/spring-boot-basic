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

import com.sales.models.Product;
import com.sales.services.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/showProducts")
	public void showProducts(Model m) {
		
		ArrayList<Product> products = productService.getProductList();
		
		for(Product p : products) {
			System.out.println(p.getpId() + " " + p.getpDesc() + " " + p.getQtyInStock());
		}
		
		m.addAttribute("products", products);
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public String getProduct(@ModelAttribute ("newProduct") Product p) {
		return "addProduct";
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public String postProduct(@Valid @ModelAttribute("newProduct") Product p, BindingResult res, Model m) {
		
		try {
			productService.addProduct(p);
			
			return "redirect:showProducts";
		} catch (Exception e) {
			System.out.println("Invalid Form Submission");
			//e.printStackTrace();
			return null;
		}
		
	}
}
