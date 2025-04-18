package com.diego_peirats.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diego_peirats.domain.entity.Transaction;
import com.diego_peirats.domain.service.TransactionService;
import com.diego_peirats.infrastructure.repository.TransactionRepository;

import transaction.TransactionDto;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void saveTransaction(TransactionDto transaction) {
		transaction.setStatus("SUCCESS");
		Transaction transa = modelMapper.map(transaction, Transaction.class);
		repository.save(transa);
		
	}

}