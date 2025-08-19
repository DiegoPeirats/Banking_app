package com.diego_peirats.application.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.diego_peirats.domain.entity.Fee;
import com.diego_peirats.domain.entity.Loan;
import com.diego_peirats.domain.service.FeeService;
import com.diego_peirats.infrastructure.client.UserClient;
import com.diego_peirats.infrastructure.repository.FeeRepository;

import loan.FeeStatus;
import loan.request.FeeRequest;
import loan.response.FeeDto;
import user.response.BankResponse;

@Service
public class FeeServiceImpl implements FeeService{
	
	@Autowired
	private FeeRepository feeRepository;
	
	@Autowired 
	private UserClient userClient;
	
	@Autowired
	private ModelMapper modelMapper;


	private FeeDto applyFee(FeeRequest request) {
		
		BankResponse response = userClient.debitFee(request);
		
		Fee savedFee = feeRepository.save(Fee.builder()
				.amount(request.getAmount())
				.accountNumber(request.getAccountNumber())
				.loanId(request.getLoanId())
				.status(FeeStatus.OPEN)
				.build());
		
		return modelMapper.map(savedFee, FeeDto.class);
	}
	
	private FeeDto updatedPaidFee(Long id) {
		Optional<Fee> optionalFee =feeRepository.findById(id);
		
		if (optionalFee.isEmpty()) return null;
		
		optionalFee.get().setStatus(FeeStatus.CLOSED);
		
		feeRepository.save(optionalFee.get());
		
		return modelMapper.map(optionalFee.get(), FeeDto.class);
		
	}
	

	@Override
    public void applyMonthlyFees(FeeRequest request) {
            
    	FeeDto feeDto = applyFee(request);
    	updatedPaidFee(feeDto.getId());
        
    }

}
