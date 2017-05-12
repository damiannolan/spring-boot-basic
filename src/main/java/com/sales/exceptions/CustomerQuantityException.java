package com.sales.exceptions;

import com.sales.models.Order;

@SuppressWarnings("serial")
public class CustomerQuantityException extends Exception {

	Order order;

	public CustomerQuantityException(String e, Order order) {
		super(e);
		this.order = order;
	}

	public Order getOrder() {
		return this.order;
	}
}
