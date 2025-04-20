package com.diego_peirats.infrastructure.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.diego_peirats.application.handler.CurrencyHandler;

@Configuration
public class CurrencyRouter {
    
	@Bean
    RouterFunction<ServerResponse> routes(CurrencyHandler handler) {

    	return RouterFunctions
    			.route(RequestPredicates.GET("/currency/{currency}"), handler::getCurrencyRate)
    			.andRoute(RequestPredicates.GET("/currencies"), handler::getCurrencies)
    			.andRoute(RequestPredicates.POST("/transformed"), handler::getTransformedBalance);
    }

}
