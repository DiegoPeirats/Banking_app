package com.diego_peirats.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import assistant.Answer;
import assistant.Question;

@FeignClient(name="assistant-service", path="/assistant-app/api/v1")
public interface AssistantClient {
	
	@PostMapping("/assistant")
	Answer getAnswer(Question question);

}

