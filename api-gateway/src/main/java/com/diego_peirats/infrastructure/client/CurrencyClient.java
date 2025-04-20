package com.diego_peirats.infrastructure.client;

import java.util.Currency;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="coin-currency-service", path="/currency-app/api/v1")
public interface CurrencyClient {
	
	@GetMapping("/currencies")
	ResponseEntity<Set<Currency>> getCurrencies();

}
