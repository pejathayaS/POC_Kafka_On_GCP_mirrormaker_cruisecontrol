package com.casestudy.kafka.kafkaconsumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
/**
 * this is a sample integration of kafka with Spring boot. This feature is available in spring boot out of the box.
 * This listens to the configured topic and assumes the group_id
 */
public class KafkaConsumerService {
 /*   @KafkaListener(topics = "mirrormaker-1",groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message :" + message);
    }*/
}
