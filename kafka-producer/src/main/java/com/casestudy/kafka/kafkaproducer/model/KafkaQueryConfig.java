package com.casestudy.kafka.kafkaproducer.model;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaQueryConfig {
    String bootStrapAddress;
    String timeOutInMilliSeconds;
}
