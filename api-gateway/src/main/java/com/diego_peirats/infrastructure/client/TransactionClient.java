package com.diego_peirats.infrastructure.client;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import transaction.TransactionDto;
import transaction.request.BankStatementRequest;

@FeignClient(name="transaction-service", path="/transaction-app/api/v1")
public interface TransactionClient {
	
	@PostMapping
	public List<TransactionDto> generateBankStatement(
			@RequestBody BankStatementRequest request);

}
