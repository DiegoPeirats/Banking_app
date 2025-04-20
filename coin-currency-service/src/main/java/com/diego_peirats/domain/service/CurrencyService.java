package com.diego_peirats.domain.service;
import org.springframework.web.reactive.function.server.ServerResponse;

import currency.request.TransformRequest;
import reactor.core.publisher.Mono;

public interface CurrencyService {
	
	Mono<ServerResponse> getCurrency(String coin);
	
	Mono<ServerResponse> getCurrencies();
	
	Mono<ServerResponse> getTransformedBalance(TransformRequest request);

}
