package transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDto {
	
	private String transactionType;
	private BigDecimal amount;
	private String accountNumber;
	private String status;
	private LocalDate createdAt;
	private LocalDate modifiedAt;

}
