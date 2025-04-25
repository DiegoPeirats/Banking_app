package com.diego_peirats.infrastructure.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

import alert.AlertEvent;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import transaction.TransactionEvent;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Slf4j
public class TransactionProducer {
	
	private NewTopic topic;
	
	private KafkaTemplate<String, TransactionEvent> kafkaTemplate;

	public TransactionProducer(NewTopic topic, KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
		this.topic = topic;
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(TransactionEvent event) {
		log.info(String.format("Transaction event => %s", event.toString()));
		
		Message<TransactionEvent> message = MessageBuilder
				.withPayload(event)
				.setHeader(KafkaHeaders.TOPIC, topic.name())
				.build();
		
		kafkaTemplate.send(message);
	}
	
	public void sendAlert(AlertEvent event) {
		log.info(String.format("Alert event => %s", event.toString()));
		
		Message<AlertEvent> message = MessageBuilder
				.withPayload(event)
				.setHeader(KafkaHeaders.TOPIC, "alert_topics")
				.build();
		
		kafkaTemplate.send(message);
	}

}
