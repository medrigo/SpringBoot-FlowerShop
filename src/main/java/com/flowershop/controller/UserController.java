package com.flowershop.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flowershop.model.User;
import com.flowershop.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@ModelAttribute("user")
	public User usermodel() {
		return new User();
	}
	
	@GetMapping("/regisform")
	public String regisform() {
		return "register";
	}
	
	@PostMapping("/register") 
	public String registation(@ModelAttribute("user")User user, @RequestParam("cpass")String cpass) {
		System.out.println(cpass);
		System.out.println(user.getPassword());
		if(!cpass.equals(user.getPassword())) {
			return "redirect:/user/regisform?pnm";
		}
		System.out.println(user.getPassword());
		String pass = MD5Hash(user.getPassword());
		user.setPassword(pass);
		user.setUser_type("user");
		System.out.println(user);
		userService.addUser(user);
		return "redirect:/user/regisform?success";
	}

	private String MD5Hash(String pass) {
		String md5Hash = null;
		try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(pass.getBytes());

            // Convert the byte array to a hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            md5Hash = sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
		return md5Hash;
	}
	
	@GetMapping("/loginform")
	public String loginform() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String email,@RequestParam String password,HttpServletRequest request) {
		String pass = MD5Hash(password);
		boolean user = userService.login(email, pass, request);
		if(user)
			return "redirect:/";
		else
			return "redirect:/user/loginform?error";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session=request.getSession(true);
		session.invalidate();
		return "redirect:/user/loginform";
	}
	
	@PostMapping("delete")
	public String delete(@RequestParam("id")Long id) {
		userService.deleteUserById(id);
		return "redirect:/admin/user?success";
	}
}
