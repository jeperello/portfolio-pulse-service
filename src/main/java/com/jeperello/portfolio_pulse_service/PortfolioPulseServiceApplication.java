package com.jeperello.portfolio_pulse_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = {KafkaAutoConfiguration.class})
public class PortfolioPulseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioPulseServiceApplication.class, args);
	}

}
