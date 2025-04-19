package com.diego_peirats.infrastructure.controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego_peirats.application.service.BankStatementService;
import com.itextpdf.text.DocumentException;

import transaction.TransactionDto;
import transaction.request.BankStatementRequest;

@RestController
@RequestMapping("/bankStatement")
public class TransactionController {
	
	@Autowired
	private BankStatementService bankStatementService;
	
	@PostMapping
	public List<TransactionDto> generateBankStatement(
			@RequestBody BankStatementRequest request) throws FileNotFoundException, DocumentException{
		String [] startNumbers = request.getStartDate().split("/");
		String [] endNumbers = request.getEndDate().split("/");
		LocalDate start = LocalDate.of(Integer.parseInt(startNumbers[0]), Integer.parseInt(startNumbers[1]), Integer.parseInt(startNumbers[2]));
		LocalDate end = LocalDate.of(Integer.parseInt(endNumbers[0]), Integer.parseInt(endNumbers[1]), Integer.parseInt(endNumbers[2]));
		return bankStatementService.generateStatement(request.getAccountNumber(), start, end);
	}

}
