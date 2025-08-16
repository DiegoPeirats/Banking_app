package user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class EnquiryRequest {
	
	private Long userId;
	
	private String accountNumber;

}
