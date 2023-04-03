package com.flowershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowershop.model.Product;
import com.flowershop.repository.ProductRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
	@Autowired
	public final ProductRepo productRepo;
	
	public ProductService(ProductRepo productRepo) {
		this.productRepo = productRepo;
	}
	
	public Product addProduct(Product product) {
		return productRepo.save(product);
	}
	
	public Product updateProduct(Product product) {
		return productRepo.save(product);
	}
	
	public Product findProductById(Long id) {
		return productRepo.findProductById(id);
	}
	
	public List<Product> findAllProducts() {
		return productRepo.findAll();
	}
	
	public void deleteProductById(Long id) {
		productRepo.deleteProductById(id);
	}
	
	public List<Product> findProductsByName(String name) {
		return productRepo.findProductsByName(name);
	}
}
