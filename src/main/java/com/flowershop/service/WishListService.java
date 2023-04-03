package com.flowershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowershop.model.WishList;
import com.flowershop.repository.WishListRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class WishListService {
	@Autowired
	public final WishListRepo wishListRepo;
	
	public WishListService(WishListRepo wishListRepo) {
		this.wishListRepo = wishListRepo;
	}
	
	public WishList addWishList(WishList wishList) {
		return wishListRepo.save(wishList);
	}
	
	public WishList updateWishList(WishList wishList) {
		return wishListRepo.save(wishList);
	}
	
	public WishList findWishListById(Long id) {
		return wishListRepo.findWishListById(id);
	}
	
	public List<WishList> findAllWishLists() {
		return wishListRepo.findAll();
	}
	
	public void deleteWishListById(Long id) {
		wishListRepo.deleteWishListById(id);
	}
	
	public List<WishList> findAllWishListsByUserId(Long id) {
		return wishListRepo.findWishListByUserId(id);
	}
	
	public List<WishList> checkWishListNumber(String name, Long id) {
		return wishListRepo.checkWishListNumber(name,id);
	}
	
	public void deleteWishLishByUserId(Long id) {
		wishListRepo.deleteWishListByUserId(id);
	}
}
