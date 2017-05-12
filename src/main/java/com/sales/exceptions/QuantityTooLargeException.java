package com.sales.exceptions;

import com.sales.models.Order;
import com.sales.models.Product;

@SuppressWarnings("serial")
public class QuantityTooLargeException extends Exception {
	
	Order order;
	Product product;
	
	public QuantityTooLargeException(String e, Order order) {
		super(e);
		this.order = order;
		
		this.product = order.getProd();
	}
	
	public Order getOrder() {
		return this.order;
	}
}
