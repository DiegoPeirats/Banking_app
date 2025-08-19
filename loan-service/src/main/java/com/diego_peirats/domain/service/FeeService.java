package com.diego_peirats.domain.service;


import loan.request.FeeRequest;
import loan.response.FeeDto;

public interface FeeService {
	
	void applyMonthlyFees(FeeRequest request);

}
