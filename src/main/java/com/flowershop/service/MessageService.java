package com.flowershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowershop.model.Message;
import com.flowershop.repository.MessageRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MessageService {
	@Autowired
	public final MessageRepo messageRepo;
	
	public MessageService(MessageRepo messageRepo) {
		this.messageRepo = messageRepo;
	}
	
	public Message addMessage(Message message) {
		return messageRepo.save(message);
	}
	
	public Message updateMessage(Message message) {
		return messageRepo.save(message);
	}
	
	public Message findMessageById(Long id) {
		return messageRepo.findMessageById(id);
	}
	
	public List<Message> findAllMessages() {
		return messageRepo.findAll();
	}
	
	public void deleteMessageById(Long id) {
		messageRepo.deleteMessageById(id);
	}
}
