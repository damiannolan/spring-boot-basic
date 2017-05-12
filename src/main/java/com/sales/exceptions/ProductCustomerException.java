package com.sales.exceptions;

import com.sales.models.Order;

@SuppressWarnings("serial")
public class ProductCustomerException extends Exception {
	
	Order order;
	
	public ProductCustomerException(String e, Order order) {
		super(e);
		this.order = order;
	}
	
	public Order getOrder() {
		return this.order;
	}
}
