package com.diego_peirats.infrastructure.controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diego_peirats.application.service.BankStatementService;
import com.diego_peirats.application.service.TransactionServiceImpl;
import com.diego_peirats.domain.entity.Transaction;
import com.itextpdf.text.DocumentException;

@RestController
@RequestMapping("/bankStatement")
public class TransactionController {
	
	@Autowired
	private BankStatementService bankStatementService;
	@Autowired
	private TransactionServiceImpl transactionService;
	
	@GetMapping()
	public List<Transaction> generateBankStatement(
			@RequestParam String accountNumber,
			@RequestParam String startDate,
			@RequestParam String endDate) throws FileNotFoundException, DocumentException{
		String [] startNumbers = startDate.split("/");
		String [] endNumbers = endDate.split("/");
		LocalDate start = LocalDate.of(Integer.parseInt(startNumbers[0]), Integer.parseInt(startNumbers[1]), Integer.parseInt(startNumbers[2]));
		LocalDate end = LocalDate.of(Integer.parseInt(endNumbers[0]), Integer.parseInt(endNumbers[1]), Integer.parseInt(endNumbers[2]));
		return bankStatementService.generateStatement(accountNumber, start, end);
	}

}
