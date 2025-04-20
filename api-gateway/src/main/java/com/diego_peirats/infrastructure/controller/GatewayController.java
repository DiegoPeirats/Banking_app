package com.diego_peirats.infrastructure.controller;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego_peirats.application.service.GatewayServiceImpl;

import assistant.Answer;
import assistant.Question;
import currency.request.TransformRequest;
import transaction.TransactionDto;
import transaction.request.BankStatementRequest;
import user.EnquiryRequest;
import user.UserDto;
import user.request.TransferRequest;
import user.response.BankResponse;

@RestController
@RequestMapping("api/v1")
public class GatewayController {
	
	@Autowired
	private GatewayServiceImpl service;
	
	@PostMapping("/bankState")
	public List<TransactionDto> generateBankStatement(
			@RequestBody BankStatementRequest request){
		return service.generateBankStatement(request);
	}
	
	@GetMapping("/balance")
	public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request) {
		return service.balanceEnquiry(request);
	}
	
	@GetMapping("/name")
	public String nameEnquiry(@RequestBody EnquiryRequest request) {
		return service.nameEnquiry(request);
	}
	
	@PostMapping("/transfer")
	public BankResponse transfer(@RequestBody TransferRequest request) {
		return service.transfer(request);
	}

	@PostMapping("/user")
	public ResponseEntity<UserDto> userByAccountNumber(@RequestBody EnquiryRequest request){
		return service.userByAccountNumber(request);
	}
	
	@PostMapping("/assistant")
	public Answer getAnswer(@RequestBody Question question) {
		return service.getAssistantAnswer(question);
	}

	@PostMapping("/transformed")
	public ResponseEntity<BigDecimal> getCurrencyTransformed(TransformRequest request){
		return service.getCurrencyValue(request);
	}
	
	@GetMapping("/currencies")
	public ResponseEntity<Set<Currency>> getCurrencies(){
		return service.getCurrencies();
	}

}
