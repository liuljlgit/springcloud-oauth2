package com.cloud.hystrixdashboard.hystrixdashboardserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class HystrixdashboardServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixdashboardServerApplication.class, args);
	}
}
