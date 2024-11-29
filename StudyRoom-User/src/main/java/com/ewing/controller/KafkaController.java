package com.ewing.controller;

import com.ewing.producer.KafkaProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ewing
 * @Date: 2024-11-22-13:05
 * @Description:
 */
@RestController
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

//    @GetMapping("/send")
//    public Void sendReplyMessage(@RequestParam String message) {
//        kafkaProducer.sendReplyMessage("notifications", message);
//        return null;
//    }
}