package com.diego_peirats.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import email.EmailDetails;

@FeignClient(name="email-service", url="http://localhost:8082")
public interface EmailClient {
	@PostMapping("/api/v1/simple-mail")
	void simpleMail(EmailDetails details);

	@PostMapping("/api/v1/attachment-mail")
	void attachmentMail(EmailDetails details);
}