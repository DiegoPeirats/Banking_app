package com.diego_peirats.application.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.diego_peirats.domain.entity.Fee;
import com.diego_peirats.domain.entity.Loan;
import com.diego_peirats.domain.service.FeeService;
import com.diego_peirats.domain.service.LoanService;
import com.diego_peirats.infrastructure.client.UserClient;
import com.diego_peirats.infrastructure.repository.LoanRepository;

import loan.FeeStatus;
import loan.InterestType;
import loan.LoanStatus;
import loan.LoanType;
import loan.request.FeeRequest;
import loan.request.LoanRequest;
import loan.response.LoanDto;
import user.EnquiryRequest;
import user.UserDto;
import user.response.AccountInfo;
import user.response.BankResponse;

@Service
public class LoanServiceImpl implements LoanService{
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private FeeServiceImpl feeService;
	
	@Autowired 
	private ModelMapper modelMapper;

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
			
			Double interest = request.getType().getInterestRate() + user.getRiskLevel().getBaseInterestRate();
			
			BigDecimal feeAmount = request.getAmount()
			        .multiply(BigDecimal.valueOf((interest / 100) + 1));
			
			Loan loan = loanRepository.save(
				    Loan.builder()
				        .userAccountNumber(user.getAccountNumber()) 
				        .amount(request.getAmount())
				        .initialDate(LocalDate.now())
				        .interestType(request.getInterestType())
				        .totalInterest(interest)
				        .expectedEnd(LocalDate.now().plusMonths(request.getNumberOfMonths()))
				        .type(request.getType())
				        .monthlyFee(feeAmount)
				        .remainingAmount(request.getAmount())
				        .status(LoanStatus.OPEN)
				        .build()
				);
			
			debitFeesAndRecalculateLoan(loan.getUserAccountNumber());
			
			
			return userClient.processLoan(request);
		}catch(Exception e) {
			
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	@Scheduled(cron = "0 0 0 1 * ?")
	private void debitFeesAndRecalculateLoan(String accountNumber) {
		
		List<Loan> loanList = loanRepository.findAllByAccountNumber(accountNumber);
		
		loanList.stream()
			.filter(loan -> loan.getStatus().equals(LoanStatus.OPEN))
			.forEach(loan -> {
				FeeRequest feeRequest = FeeRequest.builder()
						.amount(loan.getMonthlyFee())
						.accountNumber(accountNumber)
						.loanId(loan.getId())
						.dateToExpire(loan.getExpectedEnd())
						.build();
				feeService.applyMonthlyFees(feeRequest);
				
				loan.setAmount(loan.getAmount().subtract(feeRequest.getAmount()));
				
				loanRepository.save(loan);
				
				//falta recalcular en caso de que el interes sea variable
			});
		
	}

	@Override
	public BankResponse preArrangedLoan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<LoanDto>> getUserLoanHistorial(String accountNumber) {
		List<LoanDto> response = loanRepository.findAllByAccountNumber(accountNumber)
				.stream()
				.map(loan -> modelMapper.map(loan, LoanDto.class))
				.collect(Collectors.toList());
		
		if (response.size() > 0) 
			return ResponseEntity.ok(response);
		
		return ResponseEntity.notFound().build();
	}
	
	

}
