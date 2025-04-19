package com.diego_peirats.application.utils;
import java.time.Year;
import com.diego_peirats.domain.entity.User;

import user.response.AccountInfo;
import user.response.BankResponse;


public class AccountUtils {
	
	public static String generateAccountNumber() {
		Year currentYear = Year.now();
		
		int min = 100000;
		int max = 999999;
		
		int randNumber = (int) Math.floor(Math.random() * (max - min +1) +1);
		
		String year = String.valueOf(currentYear);
		String randomNumber = String.valueOf(randNumber);
		StringBuilder accountNumber = new StringBuilder();
		
		accountNumber.append(year).append(randomNumber);
		return accountNumber.toString();
	}
	
	public static BankResponse getResponse(User user, String code, String message) {
		return BankResponse.builder()
				.responseCode(code)
				.responseMessage(message)
				.accountInfo(AccountInfo.builder()
						.accountBalance(user.getAccountBalance())
						.accountNumber(user.getAccountNumber())
						.accountName(user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName())
						.build())
				.build();
	}

}
