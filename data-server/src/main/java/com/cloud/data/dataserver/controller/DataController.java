package com.cloud.data.dataserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class DataController {

    @GetMapping("/week")
    public String getTodayWeek(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date now = new Date();
        return sdf.format(now);
    }
}
