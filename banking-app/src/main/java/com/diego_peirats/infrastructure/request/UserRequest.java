package com.diego_peirats.infrastructure.request;

import com.diego_peirats.domain.entity.GenderType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	
	private String firstName;
	
	private String lastName;
	
	private String otherName;
	
	private GenderType gender;
	
	private String address;
	
	private String stateOfOrigin;
	
	private String accountNumber;
	
	private String email;
	
	private String password;
	
	private String phoneNumber;

}
