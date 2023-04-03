package com.flowershop.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.flowershop.model.Product;
import com.flowershop.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@PostMapping("/findname")
	public String findbyname(@RequestParam("search_box")String search_box, RedirectAttributes redirectAttributes) {
		List<Product> products = productService.findProductsByName(search_box);
		System.out.println(products.size());
		redirectAttributes.addFlashAttribute("products", products);	
		return"redirect:/search_page";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute("product")Product product) {
		Product updateProduct = productService.updateProduct(product);
		return "redirect:/admin/product?successupdate";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam("id")Long id) {
		productService.deleteProductById(id);
		return "redirect:/admin/product?successdelete";
	}
	
	@PostMapping("/add")
	public String add(@ModelAttribute("product")Product product) {
		Product addProduct = productService.addProduct(product);
		return "redirect:/admin/product?successadd";
	}
}
