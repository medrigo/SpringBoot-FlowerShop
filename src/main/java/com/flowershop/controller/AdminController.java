package com.flowershop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flowershop.model.Message;
import com.flowershop.model.Order;
import com.flowershop.model.Product;
import com.flowershop.model.User;
import com.flowershop.service.MessageService;
import com.flowershop.service.OrderService;
import com.flowershop.service.ProductService;
import com.flowershop.service.UserService;
import com.flowershop.service.WishListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private WishListService wishListService;
	
	@GetMapping("/contact")
	public String contact(Model model,HttpServletRequest request) {
		if(checkauth(request)) {
			List<Message> messages = messageService.findAllMessages();
			model.addAttribute("messages",messages);
			return "admin_contacts";
		}
		return "redirect:/";
	}
	
	@GetMapping("/page")
	public String page(Model model,HttpServletRequest request) {
		if(checkauth(request)) {
			Integer total_pending = (orderService.totalPending()==null)?0:orderService.totalPending();
			Integer total_completes = (orderService.totalCompletes()==null)?0:orderService.totalCompletes();
			Integer total_orders = (orderService.findAllOrders()==null)?0:orderService.findAllOrders().size();
			Integer total_products = (productService.findAllProducts()==null)?0:productService.findAllProducts().size();
			Integer total_users = (userService.findAllUsersByRole("user")==null)?0:userService.findAllUsersByRole("user").size();
			Integer total_admins = (userService.findAllUsersByRole("admin")==null)?0:userService.findAllUsersByRole("admin").size();
			Integer total_accounts = total_admins+total_users;
			Integer total_messages = (messageService.findAllMessages()==null)?0:messageService.findAllMessages().size();
			model.addAttribute("total_messages",total_messages);
			model.addAttribute("total_pending",total_pending);
			model.addAttribute("total_completes",total_completes);
			model.addAttribute("total_orders",total_orders);
			model.addAttribute("total_products",total_products);
			model.addAttribute("total_users",total_users);
			model.addAttribute("total_admins",total_admins);
			model.addAttribute("total_accounts",total_accounts);
			return "admin_page";
		}
		return "redirect:/";
		
	}
	
	@GetMapping("/product")
	public String product(Model model,HttpServletRequest request) {
		if(checkauth(request)) {
			List<Product> products = productService.findAllProducts();
			model.addAttribute("products",products);
			return "admin_products";
		}
		return "redirect:/";
	}	
	
	@GetMapping("/productupdate")
	public String productupdate(Model model,HttpServletRequest request, @RequestParam("id")Long id) {
		if(checkauth(request)) {
			Product product = productService.findProductById(id);
			model.addAttribute("product",product);
			return "admin_update_product";
		}
		return "redirect:/";
	}
	
	@GetMapping("/order")
	public String order(Model model,HttpServletRequest request) {
		if(checkauth(request)) {
			List<Order> orders = orderService.findAllOrders();
			model.addAttribute("orders",orders);
			return "admin_orders";
		}
		return "redirect:/";
	}
	
	@GetMapping("/user")
	public String user(Model model,HttpServletRequest request) {
		if(checkauth(request)) {
			List<User> users = userService.findAllUsers();
			model.addAttribute("users",users);
			return "admin_user";
		}
		return "redirect:/";
	}
	
	public boolean checkauth(HttpServletRequest request) {
		HttpSession session=request.getSession(true);
		if(session.getAttribute("user_id") != null && session.getAttribute("user_type").equals("admin")) {
			return true;
		}
		return false;
	}
}
