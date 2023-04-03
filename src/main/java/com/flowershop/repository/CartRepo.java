package com.flowershop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.flowershop.model.Cart;

import jakarta.transaction.Transactional;

public interface CartRepo extends JpaRepository<Cart, Long> {
	
	void deleteCartById(Long id);
	
	Cart findCartById(Long id);
	
	@Query(value = "SELECT id,user_id,pid,name,price,quantity,image "
			+ "FROM cart "
			+ "WHERE user_id = ?1 ",
			nativeQuery = true)
	List<Cart> findCartByUserId(Long id);
	
	@Query(value = "SELECT id,user_id,pid,name,price,quantity,image "
			+ "FROM cart "
			+ "WHERE user_id = ?2 AND name=?1",
			nativeQuery = true)
	List<Cart> checkCartNumber(String name, Long id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE cart "
			+ "SET quantity=:#{#c.quantity} "
			+ "WHERE id=:#{#c.id} ",
			nativeQuery = true)
	int updateCartQuantityById(@Param("c")Cart cart);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM cart "
			+ "WHERE user_id = ?1",
			nativeQuery = true)
	void deleteCartByUserId(Long id);
}
