package com.casestudy.kafka.kafkaproducer.controller;

import com.casestudy.kafka.kafkaproducer.constants.HttpMessageConstants;
import com.casestudy.kafka.kafkaproducer.model.KafkaQueryConfig;
import io.swagger.annotations.*;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Api(tags = "Kafka-Topic-Controller")
@RestController
public class KafkaTopicController {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTopicController.class);

    @Autowired
    AdminClient adminClient;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    @ApiOperation(value = "Kafka-Topic-controller : API to delete messages from a given topic and partition")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 500, message = HttpMessageConstants.INTERNAL_SERVER_ERROR),
    })
    @RequestMapping(value = "/producer/delete-message/{topic}/{partition}", method = { RequestMethod.POST })
    public void deleteMessageFromTopic(@ApiParam(value = "The topic ID", required = true, example = "test") @PathVariable String topic,
                                       @ApiParam(value = "The partition number of the topic", required = true, example = "1") @PathVariable int partition,
                                       @ApiParam(value = "The Configuration details", required = true, type= "KafkaQueryConfig") @RequestBody KafkaQueryConfig queryConfig) {

        AdminClient client = prepareAdminClient(queryConfig);
        //The offset of partition has been configured for 2 : this can be part of request or can be automatically retrieved
        Map<TopicPartition, RecordsToDelete> deleteMap = Map.of(
                new TopicPartition(topic, partition), RecordsToDelete.beforeOffset(3));
        try {
            client.deleteRecords(deleteMap, new DeleteRecordsOptions());
        }
        catch (Exception ex) {
            logger.error("Error while deleting the message from topic");
        }
    }
    @ApiOperation(value = "Kafka-Topic-controller : API to delete a given topic ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 500, message = HttpMessageConstants.INTERNAL_SERVER_ERROR),
    })
    @RequestMapping(value = "/producer/delete-topic/{topic}", method = { RequestMethod.POST })
    public void deleteTopic(@ApiParam(value = "The topic ID", required = true, example = "test") @PathVariable String topic,
                            @ApiParam(value = "The Configuration details", required = true, type= "KafkaQueryConfig") @RequestBody KafkaQueryConfig queryConfig) {
        try {
            AdminClient client = prepareAdminClient(queryConfig);
            client.deleteTopics(Arrays.asList(topic));
        }
        catch (Exception ex) {
            logger.error("Error while deleting the message from topic");
        }
    }

    /**
     * If the params are passed for the query the same is used else default configurations applied.
     * @param queryConfig configuration details of the CRUD query on topics
     * @return admin client instance, based on the new config details or the default one configured as part of the service (One time activity)
     */
    private AdminClient prepareAdminClient(KafkaQueryConfig queryConfig) {

        if (queryConfig != null && queryConfig.getBootStrapAddress() !=null && !queryConfig.getBootStrapAddress().isEmpty()) {
            Map<String, Object> conf = new HashMap<>();
            conf.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, queryConfig.getBootStrapAddress());
            conf.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, queryConfig.getTimeOutInMilliSeconds());
            return AdminClient.create(conf);
        }
        return adminClient;
    }

}
