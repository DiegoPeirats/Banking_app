package com.diego_peirats.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import user.UserDto;


@FeignClient(name="banking-app", url="http://localhost:8080")
public interface UserClient {
	@GetMapping("/api/v1/balance")
	UserDto getUserByAccountNumber(String accountNumber);
}
