package loan.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FeeRequest {
	
	private String accountNumber;
	
	private Long loanId;
	
	private BigDecimal amount;
	
	private LocalDate dateToExpire;

}
