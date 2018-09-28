package com.cloud.config.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {

    @Value("${cloud.name}")
    String foo;

    @RequestMapping("/hi")
    public String sayHi(){
        return foo;
    }
}
