package com.diego_peirats.application.service;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.diego_peirats.application.response.LoginDto;
import com.diego_peirats.application.response.TransactionType;
import com.diego_peirats.application.response.UserDetailsDto;
import com.diego_peirats.application.utils.AccountStatus;
import com.diego_peirats.application.utils.AccountUtils;
import com.diego_peirats.application.utils.ServiceUtils;
import com.diego_peirats.domain.entity.User;
import com.diego_peirats.domain.service.UserService;
import com.diego_peirats.infrastructure.configuration.CustomUserDetailsService;
import com.diego_peirats.infrastructure.configuration.JwtTokenProvider;
import org.springframework.security.core.userdetails.UserDetails;
import com.diego_peirats.infrastructure.repository.UserRepository;
import com.diego_peirats.infrastructure.request.CreditDebitRequest;
import com.diego_peirats.infrastructure.request.TransferRequest;
import com.diego_peirats.infrastructure.request.UserRequest;

import user.EnquiryRequest;
import user.Role;
import user.UserDto;
import user.response.BankResponse;

import static com.diego_peirats.application.utils.AccountUtils.*;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private ServiceUtils utils;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	private User user;
	
	public ResponseEntity<UserDetailsDto> getByUsername(String username) {
	    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	    UserDetailsDto dto = modelMapper.map(userDetails, UserDetailsDto.class);
	    return ResponseEntity.ok(dto);
	}
	@Override
	public BankResponse createAccount(UserRequest request) {
		
		if (repository.existsByEmail(request.getEmail())) {
			return getResponse(null, AccountStatus.ACCOUNT_NOT_FOUND.code(), AccountStatus.ACCOUNT_NOT_FOUND.message());
		}

		User user = User.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.otherName(request.getOtherName())
				.gender(request.getGender())
				.address(request.getAddress())
				.stateOfOrigin(request.getStateOfOrigin())
				.accountNumber(AccountUtils.generateAccountNumber())
				.accountBalance(BigDecimal.ZERO)
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.phoneNumber(request.getPhoneNumber())
				.status("ACTIVE")
				.role(Role.ROLE_ADMIN)
				.build();
		
		User savedUser = repository.save(user);
		
		utils.sendEmail(savedUser.getEmail(), "ACCOUNT CREATION", "Congratulations! your account has been created");
		
		return getResponse(savedUser, AccountStatus.ACCOUNT_CREATED.code(), AccountStatus.ACCOUNT_CREATED.message());
	}
	
	@Override
	public BankResponse login(LoginDto loginDto) {
		Authentication authentication = null;
		authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDto.getEmail(), 
						loginDto.getPassword()));

		utils.sendEmail(loginDto.getEmail(), "You're logged in", "You logged into your account");
		user = repository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new IllegalArgumentException());
		return getResponse(user, "Login Success", jwtTokenProvider.generateToken(authentication));
	}

	@Override
	public BankResponse balanceEnquiry(EnquiryRequest request) {
		
		return utils.findUser(request.getAccountNumber());
	}

	@Override
	public String nameEnquiry(EnquiryRequest request) {
		
		return utils.findUser(request.getAccountNumber()).getAccountInfo().getAccountName();
		
	}

	@Override
	public BankResponse creditAccount(CreditDebitRequest request) {
		
		return utils.findUserOperateAndSendTransaction(request.getAccountNumber(), request.getAmount(), TransactionType.CREDIT);
	}

	@Override
	public BankResponse debitAccount(CreditDebitRequest request) {
		
		return utils.findUserOperateAndSendTransaction(request.getAccountNumber(), request.getAmount(), TransactionType.DEBIT);
	}

	@Override
	public BankResponse transfer(TransferRequest request) {
		
		try {
			if (!request.getSourceAccountNumber().equals(user.getAccountNumber())) throw new IllegalArgumentException();
			
			BankResponse response = utils.findUserOperateAndSendTransaction(request.getSourceAccountNumber(), request.getAmount(), TransactionType.DEBIT);
			utils.findUserOperateAndSendTransaction(request.getDestinationAccountNumber(), request.getAmount(), TransactionType.CREDIT);

			return response;
		}catch(IllegalArgumentException e) {
			return getResponse(user, AccountStatus.NOT_AUTHORIZED_TRANSFER.code(), AccountStatus.NOT_AUTHORIZED_TRANSFER.message());
		}
	}
	
	@Override
	public ResponseEntity<UserDto> getUserByAccountNumber(String accountNumber) {
		try {
			User user = repository.findByAccountNumber(accountNumber).orElseThrow(
					() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
			
			return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
		}catch(RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	@Override
	public ResponseEntity<UserDto> getUserById(Long userId) {
		try {
			User user = repository.findById(userId).orElseThrow(
					() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
			
			return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
		}catch(RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
