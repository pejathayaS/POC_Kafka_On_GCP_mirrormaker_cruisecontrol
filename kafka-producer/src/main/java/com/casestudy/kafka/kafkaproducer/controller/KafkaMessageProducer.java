package com.casestudy.kafka.kafkaproducer.controller;

import com.casestudy.kafka.kafkaproducer.constants.HttpMessageConstants;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Kafka-Producer")
@RestController
public class KafkaMessageProducer{

    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageProducer.class);

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @ApiOperation(value = "Kafka-message-Producer : API to publish a message to a given topic ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully published"),
            @ApiResponse(code = 500, message = HttpMessageConstants.INTERNAL_SERVER_ERROR),
    })
    @RequestMapping(value = "/producer/publish-message/{topic}", method = { RequestMethod.POST})
    public String publishMessageAndCheckStatus(@ApiParam(value = "The topic ID", required = true, example = "test")@PathVariable String topic,
                                               @ApiParam(value = "The data to push", required = true, type= "String")@RequestBody String data) {

        try {
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    System.out.println("---success publishing Message----" + result);
                }

                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("---error----" + ex);

                }
            });
        }
        catch (Exception ex) {
            logger.error("Error while pushing the message to topic");
        }
        return "Published successfully to Kafka";
    }
}