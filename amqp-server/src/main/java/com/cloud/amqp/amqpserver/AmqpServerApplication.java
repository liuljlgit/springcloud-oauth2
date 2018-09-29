package com.cloud.amqp.amqpserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AmqpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmqpServerApplication.class, args);
	}
}
