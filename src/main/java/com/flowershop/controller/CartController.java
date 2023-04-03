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

import com.flowershop.model.Cart;
import com.flowershop.model.WishList;
import com.flowershop.service.CartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private final CartService cartService;
	
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@PostMapping("/add")
	public String addToCart(@ModelAttribute("cart")Cart cart) {
		List<Cart> carts = cartService.checkCartNumber(cart.getName(), cart.getUser_id());
		if(carts.size() > 0) {
			return "redirect:/?ic";
		} else {
			Cart addcart = cartService.addCart(cart);
			return "redirect:/";
		}
	}
	
	@PostMapping("/addshop")
	public String addToCartShop(@ModelAttribute("cart")Cart cart) {
		List<Cart> carts = cartService.checkCartNumber(cart.getName(), cart.getUser_id());
		if(carts.size() > 0) {
			return "redirect:/shop?ic";
		} else {
			Cart addcart = cartService.addCart(cart);
			return "redirect:/shop";
		}
	}
	
	@PostMapping("/addsearch")
	public String addToCartSearch(@ModelAttribute("cart")Cart cart) {
		List<Cart> carts = cartService.checkCartNumber(cart.getName(), cart.getUser_id());
		if(carts.size() > 0) {
			return "redirect:/search_page?ic";
		} else {
			Cart addcart = cartService.addCart(cart);
			return "redirect:/search_page";
		}
	}
	
	@PostMapping("/updatequantity")
	public String updateQuantity(@ModelAttribute("cart")Cart cart) {
		int updatecart = cartService.updateCartquantity(cart);
		return "redirect:/cart/page";
	}
	
	@GetMapping("/page")
	public String page(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession(true);
		if (session.getAttribute("user_id") != null) {
			Long id = (Long) session.getAttribute("user_id");
			List<Cart> carts = cartService.findAllCartsByUserId(id);
			Integer grand = 0;
			for(Cart cart : carts) {
				grand += cart.getPrice()*cart.getQuantity();
			}
			model.addAttribute("carts", carts);
			model.addAttribute("grand", grand);
			return "cart";
		}
		return "redirect:/user/loginform";
	}
	
	@PostMapping("/deleteall")
	public String deleteall(HttpServletRequest request) {
		HttpSession session=request.getSession(true);
		if (session.getAttribute("user_id") != null) {
			Long id = (Long) session.getAttribute("user_id");
			cartService.deleteCartByUserId(id);
			session.setAttribute("cartcount", 0);
			return "redirect:/cart/page?success";
		}
		return "redirect:/user/loginform";
	}
	
	@PostMapping("/delete")
	public String delete(HttpServletRequest request, @RequestParam("id")Long id) {
		HttpSession session=request.getSession(true);
		if (session.getAttribute("user_id") != null) {
			cartService.deleteCartById(id);
			Long user_id = (Long)session.getAttribute("user_id");
			int c_count = cartService.findAllCartsByUserId(user_id).size();
			session.setAttribute("cartcount", c_count);
			return "redirect:/cart/page?success";
		}
		return "redirect:/user/loginform";
	}
	
	@GetMapping("/checkout")
	public String checkout(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession(true);
		if (session.getAttribute("user_id") != null) {
			Long id = (Long) session.getAttribute("user_id");
			List<Cart> carts = cartService.findAllCartsByUserId(id);
			Integer grand = 0;
			for(Cart cart : carts) {
				grand += cart.getPrice()*cart.getQuantity();
			}
			model.addAttribute("carts", carts);
			model.addAttribute("grand", grand);
			return "checkout";
		}
		return "redirect:/user/loginform";
	}
}
