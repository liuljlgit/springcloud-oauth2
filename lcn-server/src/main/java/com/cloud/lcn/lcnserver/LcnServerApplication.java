package com.cloud.lcn.lcnserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cloud")
public class LcnServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LcnServerApplication.class, args);
	}

}

