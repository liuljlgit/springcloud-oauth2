package com.cloud.data.dataserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DataServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataServerApplication.class, args);
	}
}
