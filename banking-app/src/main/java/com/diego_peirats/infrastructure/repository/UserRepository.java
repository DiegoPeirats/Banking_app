package com.diego_peirats.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diego_peirats.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);
	
	Boolean existsByAccountNumber(String accountNumber);
	
	User findByAccountNumber(String accountNumber);

}
