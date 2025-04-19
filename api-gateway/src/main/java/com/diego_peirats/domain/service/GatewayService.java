package com.diego_peirats.domain.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import assistant.Answer;
import assistant.Question;
import transaction.TransactionDto;
import transaction.request.BankStatementRequest;
import user.EnquiryRequest;
import user.UserDto;
import user.request.TransferRequest;
import user.response.BankResponse;

public interface GatewayService {

	List<TransactionDto> generateBankStatement(BankStatementRequest request);
	
	Answer getAssistantAnswer(Question question);
	
	BankResponse balanceEnquiry(EnquiryRequest request);
	
	String nameEnquiry(EnquiryRequest request);
	
	BankResponse transfer(TransferRequest request);

	ResponseEntity<UserDto> userByAccountNumber(EnquiryRequest request);

}
