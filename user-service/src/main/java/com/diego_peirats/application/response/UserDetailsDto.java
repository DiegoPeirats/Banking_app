package com.diego_peirats.application.response;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import user.Role;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
public class UserDetailsDto implements UserDetails{
	

	private static final long serialVersionUID = -4357693878213793944L;
	private String email;
	private String password;
	private Role role;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {

		return email;
	}

}
