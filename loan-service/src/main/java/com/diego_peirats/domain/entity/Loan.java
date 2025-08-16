package com.diego_peirats.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import loan.InterestType;
import loan.LoanStatus;
import loan.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="loans")
public class Loan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	
	private BigDecimal amount;
	
	private LocalDate initialDate;
	
	private InterestType interestType;
	
	private LocalDate expectedEnd;
	
	private LoanType type;
	
	private LoanStatus status;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
	
}
