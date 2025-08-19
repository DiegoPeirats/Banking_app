package com.diego_peirats.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego_peirats.domain.entity.Fee;

import loan.response.FeeDto;

public interface FeeRepository extends JpaRepository<Fee, Long>{
	
	List<FeeDto> findAllByAccountNumber(String accountNumber);

}
