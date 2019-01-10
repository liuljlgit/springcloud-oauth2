package com.cloud.kafka.kafkaserver.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"sun"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object o = kafkaMessage.get();
            System.out.println("监听到消息...");
            System.out.println(o);
        }

    }
}
