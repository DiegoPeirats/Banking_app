package com.diego_peirats.domain.service;

import com.diego_peirats.application.response.TransactionDTO;

public interface TransactionService {
	
	void saveTransaction(TransactionDTO transaction);

}
