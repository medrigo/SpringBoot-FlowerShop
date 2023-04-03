package com.flowershop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flowershop.model.WishList;
import com.flowershop.service.WishListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/wishlist")
public class WishListController {
	@Autowired
	private final WishListService wishListService;

	public WishListController(WishListService wishListService) {
		this.wishListService = wishListService;
	}

	@ModelAttribute("wishlist")
	public WishList wishlistmodel() {
		return new WishList();
	}

	@PostMapping("/add")
	public String addToWishList(@ModelAttribute("wishlist") WishList wishList) {
		List<WishList> wishLists = wishListService.checkWishListNumber(wishList.getName(), wishList.getUser_id());
		if (wishLists.size() > 0) {
			return "redirect:/?iw";
		} else {
			WishList addWishList = wishListService.addWishList(wishList);
			return "redirect:/";
		}
	}

	@PostMapping("/addshop")
	public String addToWishListShop(@ModelAttribute("wishlist") WishList wishList) {
		List<WishList> wishLists = wishListService.checkWishListNumber(wishList.getName(), wishList.getUser_id());
		if (wishLists.size() > 0) {
			return "redirect:/shop?iw";
		} else {
			WishList addWishList = wishListService.addWishList(wishList);
			return "redirect:/shop";
		}
	}
	
	@PostMapping("/addsearch")
	public String addToWishListSearch(@ModelAttribute("wishlist") WishList wishList) {
		List<WishList> wishLists = wishListService.checkWishListNumber(wishList.getName(), wishList.getUser_id());
		if (wishLists.size() > 0) {
			return "redirect:/search_page?iw";
		} else {
			WishList addWishList = wishListService.addWishList(wishList);
			return "redirect:/search_page";
		}
	}

	@GetMapping("/page")
	public String page(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession(true);
		if (session.getAttribute("user_id") != null) {
			Long id = (Long) session.getAttribute("user_id");
			List<WishList> wishLists = wishListService.findAllWishListsByUserId(id);
			System.out.println(wishLists.size());
			Integer grand = 0;
			for(WishList wishlist : wishLists) {
				grand += wishlist.getPrice();
			}
			model.addAttribute("wishLists", wishLists);
			model.addAttribute("grand", grand);
			return "wishlist";
		}
		return "redirect:/user/loginform";
	}
	
	@PostMapping("/deleteall")
	public String deleteall(HttpServletRequest request) {
		HttpSession session=request.getSession(true);
		if (session.getAttribute("user_id") != null) {
			Long id = (Long) session.getAttribute("user_id");
			wishListService.deleteWishLishByUserId(id);
			session.setAttribute("wishListcount", 0);
			return "redirect:/wishlist/page?success";
		}
		return "redirect:/user/loginform";
	}
	
	@PostMapping("/delete")
	public String delete(HttpServletRequest request, @RequestParam("id")Long id) {
		HttpSession session=request.getSession(true);
		if (session.getAttribute("user_id") != null) {
			wishListService.deleteWishListById(id);
			Long user_id = (Long)session.getAttribute("user_id");
			int w_count = wishListService.findAllWishListsByUserId(user_id).size();
			session.setAttribute("wishListcount", w_count);
			return "redirect:/wishlist/page?success";
		}
		return "redirect:/user/loginform";
	}
}
