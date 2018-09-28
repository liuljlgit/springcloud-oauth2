package com.cloud.ribbon.ribbonserver.controller;

import com.cloud.ribbon.ribbonserver.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RibbonController {

    @Autowired
    RibbonService ribbonService;

    @RequestMapping("/week")
    public String getTodayWeek(){
        return ribbonService.getTodayWeek();
    }

}
