package com.flowershop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flowershop.model.Cart;
import com.flowershop.model.Product;
import com.flowershop.service.CartService;
import com.flowershop.service.ProductService;
import com.flowershop.service.WishListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private final WishListService wishListService;
	@Autowired
	private final CartService cartService;
	@Autowired
	private final ProductService productService;
	
	public HomeController(WishListService wishListService, 
			CartService cartService,
			ProductService productService) {
		this.wishListService = wishListService;
		this.cartService = cartService;
		this.productService = productService;
	}
	
	@ModelAttribute("cart")
	public Cart cartmodel() {
		return new Cart();
	}
	
	@GetMapping
	public String index(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession(true);
		if(session.getAttribute("user_id") != null) {
			countWishList(session);
			List<Product> products = productService.findAllProducts().subList(0, 6);
			model.addAttribute("products",products);
			return "home";
		}	
		else
			return "redirect:/user/loginform";
	}
	
	
	
	public void countWishList(HttpSession session) {
		if(session.getAttribute("user_id") != null) {
			Long id = (Long)session.getAttribute("user_id");
			int w_count = wishListService.findAllWishListsByUserId(id).size();
			session.setAttribute("wishListcount", w_count);
			int c_count = cartService.findAllCartsByUserId(id).size();
			session.setAttribute("cartcount", c_count);
		}
		
	}
	
	@GetMapping("/about")
	public String about(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession(true);
		if(session.getAttribute("user_id") != null) {
			countWishList(session);
			return "about";
		}	
		else
			return "redirect:/user/loginform";
	}
	
	@GetMapping("/shop")
	public String shop(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession(true);
		if(session.getAttribute("user_id") != null) {
			countWishList(session);
			List<Product> products = productService.findAllProducts();
			model.addAttribute("products",products);
			return "shop";
		}	
		else
			return "redirect:/user/loginform";
	}
	
	@GetMapping("/contact")
	public String contact(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession(true);
		if(session.getAttribute("user_id") != null) {
			countWishList(session);
			return "contact";
		}	
		else
			return "redirect:/user/loginform";
	}
	
	@GetMapping("/view_page")
	public String viewpage(HttpServletRequest request, Model model, @RequestParam("pid")Long id) {
		HttpSession session=request.getSession(true);
		if(session.getAttribute("user_id") != null) {
			countWishList(session);
			Product product = productService.findProductById(id);
			model.addAttribute("product",product);
			return "view_page";
		}	
		else
			return "redirect:/user/loginform";
	}
	
	@GetMapping("/search_page")
	public String searchpage(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession(true);
		if(session.getAttribute("user_id") != null) {
			countWishList(session);
			return "search_page";
		}	
		else
			return "redirect:/user/loginform";
	}
}
