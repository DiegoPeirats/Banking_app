package com.diego_peirats.infrastructure.listener;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.diego_peirats.domain.entity.Alert;
import com.diego_peirats.infrastructure.repository.AlertRepository;

import alert.AlertEvent;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlertListener {
	
	@Autowired
	private AlertRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@KafkaListener(
			topics = "${spring.kafka.topic.name}",
			groupId = "${spring.kafka.consumer.group-id}"
		)
	public void consume(AlertEvent event) {
		log.info(String.format("Transaction event recieved in stock service => %s", event.toString()));
		Alert alert = modelMapper.map(event.getAlert(), Alert.class);
		repository.save(alert);
	}

}
