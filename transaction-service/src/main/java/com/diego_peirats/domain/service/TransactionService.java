package com.diego_peirats.domain.service;

import com.diego_peirats.application.response.TransactionDto;

public interface TransactionService {
	
	void saveTransaction(TransactionDto transaction);

}
