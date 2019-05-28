package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.example.demo.dao")
@EnableEurekaClient
public class ServiceOauthApplication {

//	@Bean
//	public FilterRegistrationBean filterRegistrationBean() {
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		SmsCodeFilter filter = new SmsCodeFilter();
//		registrationBean.setFilter(filter);
//		List<String> urlPatterns = new ArrayList<String>();
//		urlPatterns.add("/mobile/token");
//		registrationBean.setUrlPatterns(urlPatterns);
//		return registrationBean;
//	}

	public static void main(String[] args) {
		SpringApplication.run(ServiceOauthApplication.class, args);
	}

}
