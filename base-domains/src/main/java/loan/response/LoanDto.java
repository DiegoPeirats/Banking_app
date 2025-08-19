package loan.response;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class LoanDto {
	
	private Long id;
	
	private Long userId;
	
	private BigDecimal amount;
	
	private LocalDate initialDate;
	
	private InterestType interestType;
	
	private LocalDate expectedEnd;
	
	private LoanType type;
	
	private LoanStatus status;
	
	private Double totalInterest;
	
	private BigDecimal monthlyFee;
	
	private BigDecimal remainingAmount;

}
