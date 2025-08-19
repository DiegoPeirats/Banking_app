package com.diego_peirats.domain.service;

import org.springframework.http.ResponseEntity;
import com.diego_peirats.application.response.LoginDto;
import com.diego_peirats.infrastructure.request.CreditDebitRequest;
import com.diego_peirats.infrastructure.request.TransferRequest;
import com.diego_peirats.infrastructure.request.UserRequest;

import loan.request.FeeRequest;
import loan.request.LoanRequest;
import user.EnquiryRequest;
import user.UserDto;
import user.response.BankResponse;

public interface UserService {
	
	BankResponse createAccount(UserRequest request);
	
	BankResponse balanceEnquiry(EnquiryRequest request);
	
	String nameEnquiry(EnquiryRequest request);
	
	BankResponse creditAccount(CreditDebitRequest request);

	BankResponse debitAccount(CreditDebitRequest request);

	BankResponse transfer(TransferRequest request);
	
	BankResponse login(LoginDto loginDto);

	ResponseEntity<UserDto> getUserByAccountNumber(String accountNumber);
	
	BankResponse processLoan(LoanRequest request);
	
	BankResponse debitFee(FeeRequest request);
}
