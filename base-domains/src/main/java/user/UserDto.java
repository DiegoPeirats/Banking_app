package user;

import java.math.BigDecimal;

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
public class UserDto {
	
	private String firstName;
	
	private String lastName;
	
	private String otherName;
	
	private GenderType gender;
	
	private String address;
	
	private String stateOfOrigin;
	
	private String accountNumber;
	
	private BigDecimal accountBalance;
	
	private String email;
	
	private String password;
	
	private String phoneNumber;
	
	private String status;
	
	private Role role;

}
