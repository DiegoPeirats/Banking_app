package com.diego_peirats.application.utils;

import static com.diego_peirats.application.utils.AccountUtils.getResponse;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.diego_peirats.application.response.TransactionType;
import com.diego_peirats.domain.entity.User;
import com.diego_peirats.infrastructure.client.EmailClient;
import com.diego_peirats.infrastructure.kafka.TransactionProducer;
import com.diego_peirats.infrastructure.repository.UserRepository;

import alert.AlertDto;
import alert.AlertEvent;
import email.EmailDetails;
import transaction.TransactionDto;
import transaction.TransactionEvent;
import user.response.BankResponse;

@Service
public class ServiceUtils {
	
	@Autowired
	private EmailClient emailClient;
	
	@Autowired
	private TransactionProducer transactionProducer;	
	
	@Autowired
	private UserRepository repository;

	
	public void sendEmail(String email, String subject, String body) {
		EmailDetails emailDetails = EmailDetails.builder()
				.recipient(email)
				.subject(subject)
				.messageBody(body)
				.attachment(null)
				.build();
		
		emailClient.simpleMail(emailDetails);
	}
	
	private void sendTransactionToSave(User user, BigDecimal amount, String type) {
		try {
			TransactionDto transaction = TransactionDto.builder()
					.accountNumber(user.getAccountNumber())
					.transactionType("CREDIT")
					.amount(amount)
					.build();
			TransactionEvent event = TransactionEvent.builder()
					.message("Transaction event")
					.status("ACCEPTED")
					.transaction(transaction)
					.build();
			transactionProducer.sendMessage(event);
		}catch(Exception e) {
			AlertEvent event = AlertEvent.builder()
					.message(e.getMessage())
					.status(HttpStatus.NOT_ACCEPTABLE)
					.alert(AlertDto.builder()
							.accountId(user.getId())
							.message("ABORTED TRANSACTION")
							.build())
					.build();
		}
	}

	
	public BankResponse findUserOperateAndSendTransaction(String accountNumber, BigDecimal amount, TransactionType type) {
		
		if (!repository.existsByAccountNumber(accountNumber)) {
			return BankResponse.builder()
					.responseCode(AccountStatus.ACCOUNT_NOT_FOUND.code())
					.responseMessage(AccountStatus.ACCOUNT_NOT_FOUND.message())
					.accountInfo(null)
					.build();
		}
		
		try {
			User user = repository.findByAccountNumber(accountNumber).orElseThrow(() -> new IllegalArgumentException());
			if (type.name().equalsIgnoreCase("CREDIT")) user.setAccountBalance(user.getAccountBalance().add(amount));
			else {
				if (user.getAccountBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) 
					return AccountUtils.getResponse(user, AccountStatus.INSUFFICIENT_BALANCE.code(), AccountStatus.INSUFFICIENT_BALANCE.message());
				else user.setAccountBalance(user.getAccountBalance().subtract(amount));
			}
			repository.save(user);
			
			sendTransactionToSave(user, amount, type.name());
			sendEmail(user.getEmail(),
					type +" ALERT", 
					amount + " has been "+ (type.name().equalsIgnoreCase("CREDIT")? "added to" : "deducted from ") +" your account");

			return AccountUtils.getResponse(
					user, 
					type.name().equalsIgnoreCase("CREDIT")? AccountStatus.ACCOUNT_CREDITED.code() : AccountStatus.ACCOUNT_DEBITED.code(), 
					type.name().equalsIgnoreCase("CREDIT")? AccountStatus.ACCOUNT_CREDITED.message() : AccountStatus.ACCOUNT_DEBITED.message());
		}catch(IllegalArgumentException e) {
			return AccountUtils.getResponse(null, AccountStatus.ACCOUNT_NOT_FOUND.code(), AccountStatus.ACCOUNT_NOT_FOUND.message());
		}
	}

	public BankResponse findUser(String accountNumber) {
		if (!repository.existsByAccountNumber(accountNumber)) 
			return BankResponse.builder()
					.responseCode(AccountStatus.ACCOUNT_NOT_FOUND.code())
					.responseMessage(AccountStatus.ACCOUNT_NOT_FOUND.message())
					.accountInfo(null)
					.build();
		
		try {
			User foundUser = repository.findByAccountNumber(accountNumber).orElseThrow(() -> new IllegalArgumentException());
			
			return getResponse(foundUser, AccountStatus.ACCOUNT_FOUND.code(), AccountStatus.ACCOUNT_FOUND.message());
		}catch(IllegalArgumentException e) {
			return getResponse(null, AccountStatus.ACCOUNT_NOT_FOUND.code(), AccountStatus.ACCOUNT_NOT_FOUND.message());
		}
	}
	
}
