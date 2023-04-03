package com.flowershop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flowershop.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
	void deleteOrderById(Long id);
	
	Order findOrderById(Long id);
	
	@Query(value = "SELECT id,user_id,name,number,email,method,address,total_products,total_price,placed_on,payment_status "
			+ "FROM orders "
			+ "WHERE user_id = ?1 ",
			nativeQuery = true)
	List<Order> findOrderByUserId(Long id);
	
	@Query(value = "SELECT SUM(total_price) "
			+ "FROM orders "
			+ "WHERE payment_status = 'pending'",
			nativeQuery = true)
	Integer findTotalByPending();
	
	@Query(value = "SELECT SUM(total_price) "
			+ "FROM orders "
			+ "WHERE payment_status = 'completed'",
			nativeQuery = true)
	Integer findTotalByConpletes();
}
