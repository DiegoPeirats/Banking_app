package com.diego_peirats.application.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diego_peirats.domain.service.LoanService;
import com.diego_peirats.infrastructure.client.UserClient;
import com.diego_peirats.infrastructure.repository.LoanRepository;

import loan.LoanStatus;
import loan.request.LoanRequest;
import loan.response.LoanDto;
import user.EnquiryRequest;
import user.UserDto;
import user.response.AccountInfo;
import user.response.BankResponse;

@Service
public class LoanServiceImpl implements LoanService{
	
	@Autowired
	private LoanRepository repository;
	
	@Autowired
	private UserClient userClient;

	@Override
	public BankResponse acceptLoan(LoanRequest request) {
		
		try {
			UserDto user = userClient.getUserByAccountNumber(new EnquiryRequest(request.getAccountNumber()));
			
			if (user.getAccountBalance()
					.multiply(BigDecimal.valueOf(request.getType().getMinAmountInAccount()))
					.compareTo(request.getAmount()) < 0) {
				return new BankResponse("011", "The account doesn´t have the minimum amount", 
						new AccountInfo(user.getFirstName() + user.getLastName(), user.getAccountBalance(), user.getAccountNumber())); 
			}
			
			List<LoanDto> historial = getUserLoanHistorial(request.getAccountNumber()).getBody();
			
			if (historial.stream()
				.anyMatch(loan -> loan.getStatus() == LoanStatus.OPEN)) {
				return new BankResponse("011", "You already have a open loan", 
						new AccountInfo(user.getFirstName() + user.getLastName(), user.getAccountBalance(), user.getAccountNumber()));  
			}
			
			return userClient.processLoan(request);
		}catch(Exception e) {
			
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public BankResponse preArrangedLoan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<LoanDto>> getUserLoanHistorial(String accountNumber) {
		List<LoanDto> response = repository.findAllByAccountNumber(accountNumber);
		
		if (response.size() > 0) 
			return ResponseEntity.ok(response);
		
		return ResponseEntity.notFound().build();
	}
	
	

}
