package com.cloud.kafka.kafkaserver.provider;

import com.alibaba.fastjson.JSON;
import com.cloud.kafka.kafkaserver.entity.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class KafkaProvider {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String msg) {
        Message message = new Message(System.currentTimeMillis(),msg,new Date());
        kafkaTemplate.send("sun", JSON.toJSONString(message));
    }

}
