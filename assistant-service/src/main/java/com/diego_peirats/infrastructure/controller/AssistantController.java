package com.diego_peirats.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego_peirats.application.service.OpenAiService;
import assistant.Answer;
import assistant.Question;

@RestController
@RequestMapping("/api/v1")
public class AssistantController {
	
	@Autowired
	private OpenAiService service;
	
	@PostMapping("/assistant")
	public Answer getAnswer(@RequestBody Question question) {
		return service.getResult(question);
	}

}
