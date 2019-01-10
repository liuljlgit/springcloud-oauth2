package com.cloud.kafka.kafkaserver.controller;

import com.cloud.kafka.kafkaserver.provider.KafkaProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class KafkaController {

    @Autowired
    KafkaProvider kafkaProvider;

    /**
     * SysDept 根据主键获取单个数据
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/kafka/{msg}")
    public String loadSysDeptByKey(@PathVariable(value="msg") String msg) throws Exception {
        kafkaProvider.send(msg);
        return null;
    }
}
