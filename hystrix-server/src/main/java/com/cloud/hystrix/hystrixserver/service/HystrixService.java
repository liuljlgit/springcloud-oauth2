package com.cloud.hystrix.hystrixserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HystrixService {

    @Autowired
    RestTemplate restTemplate;

    public String getTodayWeek(){
        return restTemplate.getForObject("http://DATA-SERVER/week",String.class);
    }
}
