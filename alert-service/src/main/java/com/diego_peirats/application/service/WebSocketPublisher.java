package com.diego_peirats.application.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import alert.AlertDto;

@Component
public class WebSocketPublisher {
    
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendAlert(AlertDto message) {
        messagingTemplate.convertAndSend("/topic/alerts", message);
        System.out.println("📡 Mensaje enviado a WebSocket: " + message);
    }
}

