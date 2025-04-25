package com.diego_peirats.infrastructure.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import alert.AlertDto;

@Controller
public class WSAlertController {
	
	@MessageMapping("/alert")
    @SendTo("/topic/alerts")
    public AlertDto sendAlert(AlertDto message) {
        return message; 
    }

}

