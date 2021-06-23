package com.casestudy.kafka.kafkaconsumer.config;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableKafka
@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaConfiguration {

    private String bootstrapServers;
    private String groupId;
    private String autoOffsetReset;

    @Bean
    public ConsumerFactory<String,String> consumerFactory() {
        Map<String,Object> myConfig = new HashMap<>();
        myConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        myConfig.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        myConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        myConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        myConfig.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000);
        myConfig.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 1000);
        myConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,autoOffsetReset);

        return new DefaultKafkaConsumerFactory<String,String>(myConfig);
    }

    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory =
                new ConcurrentKafkaListenerContainerFactory();

        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
    }
}
