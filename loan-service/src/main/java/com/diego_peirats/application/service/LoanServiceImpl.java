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
import user.response.BankResponse;

@Service
public class LoanServiceImpl implements LoanService{
	
	@Autowired
	private LoanRepository repository;
	
	@Autowired
	private UserClient userClient;

	@Override
	public ResponseEntity<String> acceptLoan(LoanRequest request) {
		// encontrar al usuario
		
		UserDto user = userClient.getUserById(new EnquiryRequest(request.getUserId(), null));
		
		//ver el balance de su cuenta y el tipo de credito que solicita
		
		if (user.getAccountBalance()
				.multiply(BigDecimal.valueOf(request.getType().getMinAmountInAccount()))
				.compareTo(request.getAmount()) < 0) {
			return null; //no tiene el minimo en la cuenta
		}
		
		//ver su historial de creditos
		List<LoanDto> historial = getUserLoanHistorial(request.getUserId()).getBody();
		
		if (historial.stream()
			.anyMatch(loan -> loan.getStatus() == LoanStatus.OPEN)) {
			return null; // ya tiene creditos en marcha
		}
		
		//añadir la cantidad a la cuenta
		
		//dar un resultado
		
		return "ACCEPTED";
	}

	@Override
	public BankResponse preArrangedLoan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<LoanDto>> getUserLoanHistorial(Long userId) {
		List<LoanDto> response = repository.findAllByUserId(userId);
		
		if (response.size() > 0) 
			return ResponseEntity.ok(response);
		
		return ResponseEntity.notFound().build();
	}
	
	

}
