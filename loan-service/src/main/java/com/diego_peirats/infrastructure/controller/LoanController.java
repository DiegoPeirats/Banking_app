package com.diego_peirats.infrastructure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego_peirats.application.service.LoanServiceImpl;

import loan.request.LoanRequest;
import loan.response.LoanDto;
import user.response.BankResponse;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

	@Autowired
	private LoanServiceImpl service;
	
	@PostMapping("/historial")
	public ResponseEntity<List<LoanDto>> findUserLoanHistorial(@RequestBody String accountNumber){
		return service.getUserLoanHistorial(accountNumber);
	}
	
	@PostMapping("/result")
	public BankResponse LoanRequestResult(@RequestBody LoanRequest request) {
		return service.acceptLoan(request);
	}
}
