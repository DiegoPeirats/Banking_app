package com.diego_peirats.application.service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.diego_peirats.domain.service.GatewayService;
import com.diego_peirats.infrastructure.client.AssistantClient;
import com.diego_peirats.infrastructure.client.CurrencyClient;
import com.diego_peirats.infrastructure.client.TransactionClient;
import com.diego_peirats.infrastructure.client.UserClient;

import assistant.Answer;
import assistant.Question;
import currency.request.TransformRequest;
import transaction.TransactionDto;
import transaction.request.BankStatementRequest;
import user.EnquiryRequest;
import user.UserDto;
import user.request.TransferRequest;
import user.response.BankResponse;

@Service
public class GatewayServiceImpl implements GatewayService{

	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private CurrencyClient currencyClient;
	
	@Autowired
	private TransactionClient transactionClient;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private AssistantClient assistantClient;

	@Override
	public List<TransactionDto> generateBankStatement(BankStatementRequest request){
		return transactionClient.generateBankStatement(request);
	}

	@Override
	public Answer getAssistantAnswer(Question question) {
		return assistantClient.getAnswer(question);
	}

	@Override
	public BankResponse balanceEnquiry(EnquiryRequest request) {
		return userClient.balanceEnquiry(request);
	}

	@Override
	public String nameEnquiry(EnquiryRequest request) {
		return userClient.nameEnquiry(request);
	}

	@Override
	public BankResponse transfer(TransferRequest request) {
		return userClient.transfer(request);
	}

	@Override
	public ResponseEntity<UserDto> userByAccountNumber(EnquiryRequest request) {
		return userClient.userByAccountNumber(request);
	}

	@Override
	public ResponseEntity<BigDecimal> getCurrencyValue(TransformRequest request) {
	    return webClientBuilder.build()
	        .post()
	        .uri("http://coin-currency-service/currency-app/transformed")
	        .bodyValue(request)
	        .retrieve()
	        .toEntity(BigDecimal.class)
	        .block();
	}

	@Override
	public ResponseEntity<Set<Currency>> getCurrencies() {
		return currencyClient.getCurrencies();
	}

}
