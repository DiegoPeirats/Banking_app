package com.diego_peirats.infrastructure.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.diego_peirats.domain.entity.Transaction;
import com.diego_peirats.infrastructure.repository.TransactionRepository;

import transaction.TransactionEvent;

import java.util.concurrent.CountDownLatch;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TransactionListener {
	
	private static final Logger LOGGER =LoggerFactory.getLogger(TransactionListener.class);
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	@Autowired
	private TransactionRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@KafkaListener(
			topics = "${spring.kafka.topic.name}",
			groupId = "${spring.kafka.consumer.group-id}"
		)
	public void consume(TransactionEvent event) {
		LOGGER.info(String.format("Order event recieved in stock service => %s", event.toString()));
		Transaction transaction = modelMapper.map(event.getTransaction(), Transaction.class);
		repository.save(transaction);
		latch.countDown();
		
	}
	public CountDownLatch getLatch() {
        return latch;
    }

}

