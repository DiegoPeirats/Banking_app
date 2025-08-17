package com.diego_peirats.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import loan.request.LoanRequest;
import user.EnquiryRequest;
import user.UserDto;
import user.response.BankResponse;

@FeignClient(name="user-service", url="/user-app")
public interface UserClient {
	@PostMapping("/api/v1/userById")
	UserDto getUserByAccountNumber(@RequestBody EnquiryRequest request);
	
	@PostMapping("/api/v1/processLoan")
	BankResponse processLoan(@RequestBody LoanRequest request);
}
