package com.flowershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowershop.model.Cart;
import com.flowershop.repository.CartRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartService {
	@Autowired
	private final CartRepo cartRepo;
	
	public CartService(CartRepo cartRepo) {
		this.cartRepo = cartRepo;
	}
	
	public Cart addCart(Cart cart) {
		return cartRepo.save(cart);
	}
	
	public Cart updateCart(Cart cart) {
		return cartRepo.save(cart);
	}
	
	public Cart findCartbyId(Long id) {
		return cartRepo.findCartById(id);
	}
	
	public List<Cart> findAllCarts() {
		return cartRepo.findAll();
	}
	
	public void deleteCartById(Long id) {
		cartRepo.deleteCartById(id);
	}
	
	public List<Cart> findAllCartsByUserId(Long id) {
		return cartRepo.findCartByUserId(id);
	}
	
	public List<Cart> checkCartNumber(String name, Long id) {
		return cartRepo.checkCartNumber(name, id);
	}
	
	public int updateCartquantity(Cart cart) {
		return cartRepo.updateCartQuantityById(cart);
	}
	
	public void deleteCartByUserId(Long id) {
		cartRepo.deleteCartByUserId(id);
	}
}
