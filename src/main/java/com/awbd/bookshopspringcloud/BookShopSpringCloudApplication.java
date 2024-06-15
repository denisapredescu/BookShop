package com.awbd.bookshopspringcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableFeignClients //for microservices comunication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class BookShopSpringCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookShopSpringCloudApplication.class, args);
	}

}
