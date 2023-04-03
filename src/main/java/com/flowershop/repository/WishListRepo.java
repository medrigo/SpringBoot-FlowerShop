package com.flowershop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.flowershop.model.WishList;

import jakarta.transaction.Transactional;


public interface WishListRepo extends JpaRepository<WishList, Long> {
	void deleteWishListById(Long id);
	
	WishList findWishListById(Long id);
	
	@Query(value = "SELECT id,user_id,pid,name,price,image "
			+ "FROM wishlist "
			+ "WHERE user_id = ?1 ",
			nativeQuery = true)
	List<WishList> findWishListByUserId(Long id);
	
	@Query(value = "SELECT id,user_id,pid,name,price,image "
			+ "FROM wishlist "
			+ "WHERE user_id = ?2 AND name=?1",
			nativeQuery = true)
	List<WishList> checkWishListNumber(String name, Long id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM wishlist "
			+ "WHERE user_id = ?1",
			nativeQuery = true)
	void deleteWishListByUserId(Long id);
}
