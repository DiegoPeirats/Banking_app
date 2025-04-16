package com.diego_peirats.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego_peirats.application.service.EmailServiceImpl;
import com.diego_peirats.domain.entity.EmailDetails;

@RestController
@RequestMapping("/api/v1")
public class EmailController {
	
	@Autowired
	private EmailServiceImpl service;
	
	@PostMapping("/simple-mail")
	public void simpleMail(@RequestBody EmailDetails details) {
		service.sendEmailAlert(details);
	}
	
	@PostMapping("/attachment-mail")
	public void attachmentMail(@RequestBody EmailDetails details) {
		service.sendEmailWithAttachment(details);
	}

}
