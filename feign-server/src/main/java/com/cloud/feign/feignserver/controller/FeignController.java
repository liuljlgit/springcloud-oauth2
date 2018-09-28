package com.cloud.feign.feignserver.controller;

import com.cloud.feign.feignserver.feign.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    DataService dataService;

    @RequestMapping("/week")
    public String getTodayWeek(){
        return dataService.getTodayWeek();
    }
}
