package com.flowershop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flowershop.model.Message;
import com.flowershop.service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController {
	@Autowired
	private final MessageService messageService;
	
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@ModelAttribute("message")
	public Message messagemodel() {
		return new Message();
	}
	
	@PostMapping("/send")
	public String send(@ModelAttribute("message")Message message) {
		messageService.addMessage(message);
		return "redirect:/contact?success";
	}
	
	@PostMapping("delete")
	public String delete(@RequestParam("id")Long id) {
		messageService.deleteMessageById(id);
		return "redirect:/admin/contact?success";
	}
}
