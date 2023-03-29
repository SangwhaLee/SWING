package com.swing.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MessageController {
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/send")
	public void sendMsg(@Payload Map<String,Object> data) {
		System.out.println(data.entrySet());
		simpMessagingTemplate.convertAndSend("/sub/" + data.get("roomId"), data);
	}
	
}