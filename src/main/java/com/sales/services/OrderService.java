package com.sales.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.models.Order;
import com.sales.models.Product;
import com.sales.repositories.OrderRepository;
import com.sales.repositories.ProductRepository;

@Service
public class OrderService {

	Date date;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	public ArrayList<Order> getOrderList() {

		return (ArrayList<Order>) orderRepository.findAll();
	}

	public Order addOrder(Order order) {
		date = new Date();

		order.setOrderDate(dateFormat.format(date));

		updateProductQuantity(order.getProd(), order.getQty());

		return orderRepository.save(order);
	}

	private Product updateProductQuantity(Product product, int quantity) {
		product = productRepository.findOne(product.getpId());
		product.setQtyInStock(product.getQtyInStock() - quantity);

		return productRepository.save(product);
	}

}
