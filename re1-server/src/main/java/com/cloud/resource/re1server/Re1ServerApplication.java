package com.cloud.resource.re1server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.cloud")
@MapperScan("com.cloud.**.dao.inft")
public class Re1ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Re1ServerApplication.class, args);
	}

}

