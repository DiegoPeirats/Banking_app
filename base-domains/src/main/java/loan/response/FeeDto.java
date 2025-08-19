package loan.response;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import loan.FeeStatus;
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
public class FeeDto {

	private Long id;
	
	private Long loanId;
	
	private String accountNumber;
	
	private BigDecimal amount;
	
	private FeeStatus status;
	
	private LocalDateTime createdAt;
}
