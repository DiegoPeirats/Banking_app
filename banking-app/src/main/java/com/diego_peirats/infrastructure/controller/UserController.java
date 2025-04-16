package com.diego_peirats.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diego_peirats.application.response.BankResponse;
import com.diego_peirats.application.response.LoginDto;
import com.diego_peirats.application.response.UserDto;
import com.diego_peirats.application.service.UserServiceImpl;
import com.diego_peirats.infrastructure.request.CreditDebitRequest;
import com.diego_peirats.infrastructure.request.EnquiryRequest;
import com.diego_peirats.infrastructure.request.TransferRequest;
import com.diego_peirats.infrastructure.request.UserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name= "User Account Management endpoints")
public class UserController {
	
	@Autowired
	private UserServiceImpl service;
	
	@Operation(
			summary = "Create new user account",
			description = "Creating new user and assigning new account ID")
	@ApiResponse( 
			responseCode = "201",
			description = "Http status 201 CREATED")
	@PostMapping
	public BankResponse createAccount(@RequestBody UserRequest userRequest) {
		return service.createAccount(userRequest);
	}
	
	@PostMapping("/login")
	public BankResponse login(@RequestBody LoginDto loginDto) {
		return service.login(loginDto);
	}
	
	@GetMapping("/balance")
	public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request) {
		return service.balanceEnquiry(request);
	}
	
	@GetMapping("/name")
	public String nameEnquiry(@RequestBody EnquiryRequest request) {
		return service.nameEnquiry(request);
	}
	
	@PostMapping("/credit")
	public BankResponse creditAccount(@RequestBody CreditDebitRequest request) {
		return service.creditAccount(request);
	}
	
	@PostMapping("/debit")
	public BankResponse debitAccount(@RequestBody CreditDebitRequest request) {
		return service.debitAccount(request);
	}
	
	@PostMapping("/transfer")
	public BankResponse transfer(@RequestBody TransferRequest request) {
		return service.transfer(request);
	}

	@GetMapping("/user/{accountNumber}")
	public UserDto userByAccountNumber(@RequestParam String accountNumber) {
		return service.getUserByAccountNumber(accountNumber);
	}

}
