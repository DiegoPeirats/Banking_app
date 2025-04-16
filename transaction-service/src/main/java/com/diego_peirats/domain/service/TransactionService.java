package com.diego_peirats.domain.service;

import transaction.TransactionDto;

public interface TransactionService {
	
	void saveTransaction(TransactionDto transaction);

}
