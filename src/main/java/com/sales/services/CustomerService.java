package com.sales.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.models.Customer;
import com.sales.repositories.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public ArrayList<Customer> getCustomerList() {

		return (ArrayList<Customer>) customerRepository.findAll();
	}
	
	public Customer addCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
}
