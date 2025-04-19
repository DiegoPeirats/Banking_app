package com.diego_peirats.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.diego_peirats.infrastructure.client.response.UserDetailsDto;

import user.EnquiryRequest;
import user.UserDto;
import user.request.TransferRequest;
import user.response.BankResponse;

@FeignClient(name="user-service", path="/user-app/api/user")
public interface UserClient {
	
	@GetMapping("/balance")
	public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request);
	
	@GetMapping("/name")
	public String nameEnquiry(@RequestBody EnquiryRequest request);
	
	@PostMapping("/transfer")
	public BankResponse transfer(@RequestBody TransferRequest request);

	@PostMapping("/user")
	public ResponseEntity<UserDto> userByAccountNumber(@RequestBody EnquiryRequest request);
	
	@GetMapping("/internal/user-details/{email}")
	public ResponseEntity<UserResponseDto> loadUserByUsername(@PathVariable String email);
}
