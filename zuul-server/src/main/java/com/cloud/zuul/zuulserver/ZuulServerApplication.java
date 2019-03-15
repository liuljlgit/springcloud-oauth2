package com.cloud.zuul.zuulserver;

import com.cloud.zuul.zuulserver.filter.AccessFilter;
import com.cloud.zuul.zuulserver.filter.CorsZuulFilter;
import com.cloud.zuul.zuulserver.filter.JwtTokenParseFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

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
	 * jwt解析拦截器
	 * @return jwt解析拦截器
	 */
	@Bean
	public JwtTokenParseFilter jwtTokenParseFilter(){
		return new JwtTokenParseFilter();
	}


	/**
	 * Cors过滤器
	 * @return
	 */
	@Bean
	public CorsZuulFilter corsZuulFilter(){
		return new CorsZuulFilter();
	}
}

