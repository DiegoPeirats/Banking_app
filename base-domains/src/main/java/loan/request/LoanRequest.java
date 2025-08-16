package loan.request;

import java.math.BigDecimal;

import loan.LoanType;
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
public class LoanRequest {
	
	private Long userId;
	
	private BigDecimal amount;
	
	private LoanType type;
	
	private Integer numberOfMonths;

}
