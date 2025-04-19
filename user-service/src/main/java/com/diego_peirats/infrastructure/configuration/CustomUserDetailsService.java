package com.diego_peirats.infrastructure.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.diego_peirats.application.response.UserDetailsDto;
import com.diego_peirats.domain.entity.User;
import com.diego_peirats.infrastructure.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService 
implements UserDetailsService{
	
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username).orElseThrow(
				() -> new UsernameNotFoundException(username + " not found"));

		return modelMapper.map(user, UserDetailsDto.class);
	}

}
