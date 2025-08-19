package com.diego_peirats.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego_peirats.domain.entity.Loan;

import loan.response.LoanDto;

public interface LoanRepository extends JpaRepository<Loan, Long>{
	
	List<Loan> findAllByAccountNumber(String userId);

}
