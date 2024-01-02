package com.FlightsAnalise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FlightsAnaliseApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightsAnaliseApplication.class, args);
	}

}
