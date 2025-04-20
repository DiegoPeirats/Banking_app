package com.diego_peirats.application.service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.diego_peirats.domain.service.CurrencyService;
import com.diego_peirats.infrastructure.client.CurrencyApiClient;

import currency.request.TransformRequest;
import reactor.core.publisher.Mono;
import user.response.BankResponse;

@Service
public class CurrencyServiceImpl implements CurrencyService{
	
	@Autowired
	private CurrencyApiClient client;

	@Override
	public Mono<ServerResponse> getCurrency(String coin) {
		
		return client.getCurrencyRate()
				.flatMap(responseEntity ->
						ServerResponse.status(responseEntity.getStatusCode())
						.bodyValue(responseEntity.getBody().getSpecificRate(coin.toUpperCase())));
		
	}
	
	@Override
	public Mono<ServerResponse> getCurrencies() {
		Mono<ResponseEntity<Set<Currency>>> currencies = Mono.just(ResponseEntity.ok(Currency.getAvailableCurrencies()));
		
		return currencies.flatMap(responseEntity ->
				ServerResponse.status(responseEntity.getStatusCode())
				.bodyValue(responseEntity.getBody()));
	}
	
	@Override
	public Mono<ServerResponse> getTransformedBalance(TransformRequest request){
		Mono<ResponseEntity<BankResponse>> response = client.getAccountBalance(request.getRequest());
		
		BigDecimal balance = response.block().getBody().getAccountInfo().getAccountBalance();
		
		Double value = client.getCurrencyRate().block().getBody().getSpecificRate(request.getCoin());
		
		BigDecimal transformedBalanced = balance.multiply(BigDecimal.valueOf(value));
		
		return ServerResponse.status(HttpStatus.OK).bodyValue(transformedBalanced);
			
	}

}
