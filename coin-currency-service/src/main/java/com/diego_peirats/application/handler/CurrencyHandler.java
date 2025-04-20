package com.diego_peirats.application.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.diego_peirats.application.service.CurrencyServiceImpl;
import com.diego_peirats.infrastructure.request.TransformRequest;

import reactor.core.publisher.Mono;

@Component
public class CurrencyHandler {
	
	@Autowired
	private CurrencyServiceImpl service;
	
	public Mono<ServerResponse> getCurrencyRate(ServerRequest request) {
		String coin = request.pathVariable("currency");
	    return service.getCurrency(coin);
	}
	
	public Mono<ServerResponse> getCurrencies(ServerRequest request){
		return service.getCurrencies();
	}
	
	public Mono<ServerResponse> getTransformedBalance(ServerRequest request) {
	    return request.bodyToMono(TransformRequest.class)
	        .flatMap(service::getTransformedBalance) 
	        .onErrorResume(e -> ServerResponse
	            .status(HttpStatus.BAD_REQUEST)
	            .bodyValue("Error procesando la solicitud: " + e.getMessage()));
	}

	
}
