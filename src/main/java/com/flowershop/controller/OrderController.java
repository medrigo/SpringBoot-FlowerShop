package com.flowershop.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.flowershop.model.Order;
import com.flowershop.service.CartService;
import com.flowershop.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private final OrderService orderService;
	
	@Autowired
	private CartService cartService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	@GetMapping
	public String index(Model model, HttpServletRequest request) {
		HttpSession session=request.getSession(true);
		Long user_id = (Long)session.getAttribute("user_id");
		List<Order> orders = orderService.findOrderByUserId(user_id);
		model.addAttribute("orders",orders);
		return "orders";
	}
	
	@ModelAttribute("order")
	public Order ordermodel() {
		return new Order();
	}
	
	@PostMapping("/comfirmorder")
	public String order(@ModelAttribute("order")Order order,
			@RequestParam("grand")Integer grand,
			@RequestParam("flat")String flat, 
			@RequestParam("street")String street,
			@RequestParam("city")String city,
			@RequestParam("state")String state,
			@RequestParam("country")String country,
			@RequestParam("pin_code")String pincode,
			HttpServletRequest request) {
		HttpSession session=request.getSession(true);
		String address = flat + ", " + street + ", " + city + ", " + state + ", " + country + ", " + pincode;
		String total_products = "";
		List<Cart> carts = cartService.findAllCartsByUserId(order.getUser_id());
		for(Cart cart : carts)
			total_products += cart.getName()+" ("+cart.getQuantity()+"), ";
		total_products = total_products.replaceAll("\\s*,\\s*$", "");
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		order.setAddress(address);
		order.setPlaced_on(timeStamp);
		order.setPayment_status("pending");
		order.setTotal_products(total_products);
		order.setTotal_price(grand);
		System.out.println(order.toString());
		orderService.addOrder(order);
		cartService.deleteCartByUserId(order.getUser_id());
		session.setAttribute("cartcount", 0);
		return "redirect:/cart/checkout?success";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute("order")Order order) {
		Order updateorder = orderService.findOrderById(order.getId());
		updateorder.setPayment_status(order.getPayment_status());
		orderService.updateOrder(updateorder);
		return "redirect:/admin/order?successupdate";
	}
	
	@PostMapping("/delete")
	public String delete(@ModelAttribute("order")Order order) {
		System.out.println(order.toString());		
		orderService.deleteOrderById(order.getId());
		return "redirect:/admin/order?successdelete";
	}
}
