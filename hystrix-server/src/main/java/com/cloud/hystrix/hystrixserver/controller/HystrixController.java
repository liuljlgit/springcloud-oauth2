package com.cloud.hystrix.hystrixserver.controller;

import com.cloud.hystrix.hystrixserver.service.HystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HystrixController {

    @Autowired
    HystrixService hystrixService;

    @RequestMapping("/week")
    @HystrixCommand(fallbackMethod = "error")
    public String getTodayWeek() {
        return hystrixService.getTodayWeek();
    }

    /**
     * 熔断方法
     * @return
     */
    public String error() {
        return "服务不可用！";
    }
}
