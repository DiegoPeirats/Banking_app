package com.diego_peirats.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import user.EnquiryRequest;
import user.UserDto;

@FeignClient(name="user-service", url="/user-app")
public interface UserClient {
	@PostMapping("/api/v1/user")
	UserDto getUserById(@RequestBody EnquiryRequest request);
}
