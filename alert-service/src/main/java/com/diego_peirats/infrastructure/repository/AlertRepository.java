package com.diego_peirats.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego_peirats.domain.entity.Alert;

public interface AlertRepository extends JpaRepository<Alert,Long>{

}
