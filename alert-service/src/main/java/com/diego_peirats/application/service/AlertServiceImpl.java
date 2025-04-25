package com.diego_peirats.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.diego_peirats.domain.entity.Alert;
import com.diego_peirats.domain.service.AlertService;
import com.diego_peirats.infrastructure.repository.AlertRepository;

import alert.AlertDto;
import alert.AlertEvent;

public class AlertServiceImpl implements AlertService{
	
	@Autowired
	private AlertRepository repository;
	
	@Autowired
	private WebSocketPublisher publisher;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public void sendAlert(AlertEvent alertEvent) {
		if (alertEvent != null) {
			AlertDto alertDTO = AlertDto.builder()
					.accountId(alertEvent.getAlert().getAccountId())
					.message(alertEvent.getMessage())
					.build();
			
			Alert alert = modelMapper.map(alertDTO, Alert.class);
			repository.save(alert);
			publisher.sendAlert(alertDTO);
		}
		
	}

}
