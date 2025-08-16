package com.diego_peirats.domain.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import loan.request.LoanRequest;
import loan.response.LoanDto;
import user.response.BankResponse;

public interface LoanService {
	
	ResponseEntity<String> acceptLoan(LoanRequest request);
	
	BankResponse preArrangedLoan();
	
	ResponseEntity<List<LoanDto>> getUserLoanHistorial(Long userId);

}
