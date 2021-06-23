package com.casestudy.kafka.kafkaconsumer.controller;

import com.casestudy.kafka.kafkaconsumer.config.KafkaConfiguration;
import com.casestudy.kafka.kafkaconsumer.constants.HttpMessageConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
public class KafkaConsumerController {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerController.class);

    @Autowired
    ConsumerFactory<String,String> consumerFactory;

    /**
     * Consume message. This controller function polls the messages for a topic available in Kafka.
     *
     * @param topic the topic
     * @return the string
     */
    @ApiOperation(value = "Kafka-message-Consumer : API to poll  messages from a given topic ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully subscribed and polled the messages from the topic"),
            @ApiResponse(code = 500, message = HttpMessageConstants.INTERNAL_SERVER_ERROR),
    })
    @RequestMapping(value = "/producer/consume-message/{topic}", method = {RequestMethod.GET})
    @ResponseBody
    public String consumeMessage(@ApiParam(value = "The topic ID", required = true, example = "test") @PathVariable String topic) {

        Consumer<String, String> consumer = consumerFactory.createConsumer();

        consumer.subscribe(Collections.singletonList(topic));

        // poll messages from last 10 days

        ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofDays(10));

        consumerRecords.forEach(action -> {
            System.out.println(action.value());
        });

        return "Successfully polled the messages from the topic :" + topic;
    }
}
