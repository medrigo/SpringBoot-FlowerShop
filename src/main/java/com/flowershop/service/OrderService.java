package com.flowershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowershop.model.Order;
import com.flowershop.repository.OrderRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {
	@Autowired
	public final OrderRepo orderRepo;
	
	public OrderService(OrderRepo orderRepo) {
		this.orderRepo = orderRepo;
	}
	
	public Order addOrder(Order order) {
		return orderRepo.save(order);
	}
	
	public Order updateOrder(Order order) {
		return orderRepo.save(order);
	}
	
	public Order findOrderById(Long id) {
		return orderRepo.findOrderById(id);
	}
	
	public List<Order> findAllOrders() {
		return orderRepo.findAll();
	}
	
	public void deleteOrderById(Long id) {
		orderRepo.deleteOrderById(id);
	}
	
	public List<Order> findOrderByUserId(Long id) {
		return orderRepo.findOrderByUserId(id);
	}
	
	public Integer totalPending() {
		return orderRepo.findTotalByPending();
	}
	
	public Integer totalCompletes() {
		return orderRepo.findTotalByConpletes();
	}
}
