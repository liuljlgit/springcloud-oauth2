package com.cloud.feign.feignserver.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "data-server")
public interface DataService {

    @RequestMapping(value = "/week", method = RequestMethod.GET)
    String getTodayWeek();
}
