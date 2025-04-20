package com.diego_peirats.infrastructure.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.diego_peirats.application.response.CurrenciesRates;

import reactor.core.publisher.Mono;
import user.EnquiryRequest;
import user.response.BankResponse;

@Service
public class CurrencyApiClient {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Value("${app.api-key}")
	private String apiKey;

	public Mono<ResponseEntity<CurrenciesRates>> getCurrencyRate() {
	    return webClientBuilder.build()
	        .get()
	        .uri("https://api.exchangeratesapi.io/v1/latest?access_key="+apiKey+"&format=1")
	        .retrieve()
	        .toEntity(CurrenciesRates.class);
	}
	
	public Mono<ResponseEntity<BankResponse>> getAccountBalance(EnquiryRequest request) {
	    return webClientBuilder.build()
	            .post()
	            .uri("http://user-service/user-app/api/user/balance")
	            .bodyValue(request)
	            .retrieve()
	            .toEntity(BankResponse.class);
	}


}
