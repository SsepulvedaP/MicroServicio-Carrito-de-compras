package com.microservice.microserviceshoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroServicioCarritoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServicioCarritoApplication.class, args);
	}

}
