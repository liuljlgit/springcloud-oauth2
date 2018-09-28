package com.cloud.data.dataserver.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class DataController {

    @GetMapping("/week")
    public String getTodayWeek(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date now = new Date();
        return JSON.toJSONString(sdf.format(now));
    }
}
