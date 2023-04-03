package com.flowershop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flowershop.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
	void deleteProductById(Long id);
	
	Product findProductById(Long id);
	
	@Query(value = "SELECT id,details,image,name,price "
			+ "FROM products "
			+ "WHERE name LIKE %?1% ",
			nativeQuery = true)
	List<Product> findProductsByName(String name);
}
