package transaction.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BankStatementRequest {
	
	private String accountNumber;
	private String startDate;
	private String endDate;

}
