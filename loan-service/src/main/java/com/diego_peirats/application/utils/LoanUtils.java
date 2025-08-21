package com.diego_peirats.application.utils;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import loan.BorrowerRiskLevel;
import loan.FeeDetail;
import loan.LoanType;

@Service
public class LoanUtils {
	
	public static FeeDetail calculateInterest(LoanType loanType, BorrowerRiskLevel borrowerRiskLevel, BigDecimal amount) {
		Double interest = loanType.getInterestRate() + borrowerRiskLevel.getBaseInterestRate();
		
		BigDecimal feeAmount =  amount
		        .multiply(BigDecimal.valueOf((interest / 100) + 1));
		
		return FeeDetail.builder()
				.interest(interest)
				.amount(feeAmount)
				.build();
	}

}
