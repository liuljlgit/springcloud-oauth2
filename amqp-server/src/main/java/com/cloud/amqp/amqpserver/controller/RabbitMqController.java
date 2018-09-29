package com.cloud.amqp.amqpserver.controller;

import com.cloud.amqp.amqpserver.compoment.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMqController {

    @Autowired
    Sender sender;

    @RequestMapping("/hello")
    public void sayHello(){
        sender.send();
    }
}
