package com.diego_peirats.domain.service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.function.ServerResponse;

import assistant.Answer;
import assistant.Question;
import currency.request.TransformRequest;
import reactor.core.publisher.Mono;
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
	
	ResponseEntity<BigDecimal> getCurrencyValue(TransformRequest request);
	
	ResponseEntity<Set<Currency>> getCurrencies();

}
