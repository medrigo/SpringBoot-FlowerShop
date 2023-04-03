package com.flowershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowershop.model.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
	void deleteMessageById(Long id);
	
	Message findMessageById(Long id);
}
