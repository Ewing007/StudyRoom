package com.ewing.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: Ewing
 * @Date: 2024-11-22-13:03
 * @Description
 */
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSystemInMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
