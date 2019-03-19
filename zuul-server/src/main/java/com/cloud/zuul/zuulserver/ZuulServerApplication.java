package com.cloud.zuul.zuulserver;

import com.cloud.zuul.zuulserver.filter.AccessFilter;
import com.cloud.zuul.zuulserver.filter.CorsZuulFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableOAuth2Sso
public class ZuulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulServerApplication.class, args);
	}

	/**
	 * access拦截器
	 * @return access拦截器
	 */
	@Bean
	public AccessFilter accessFilter(){
		return new AccessFilter();
	}

	/**
	 * Cors过滤器
	 * @return
	 */
	@Bean
	public CorsZuulFilter corsZuulFilter(){
		return new CorsZuulFilter();
	}


	/**
	 * restTemplate
	 * @return
	 */
	@Bean
	@LoadBalanced
	@Qualifier("restTemplate")
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

