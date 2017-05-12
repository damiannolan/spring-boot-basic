package com.sales.exceptions;

import com.sales.models.Order;

@SuppressWarnings("serial")
public class InvalidProductException extends Exception {
	
	Order order;
	
	public InvalidProductException(String e, Order order) {
		super(e);
		this.order = order;
	}
	
	public Order getOrder() {
		return this.order;
	}
}
