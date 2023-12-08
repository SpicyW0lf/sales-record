package com.example.salesrecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SalesRecordApplication {
	public static void main(String[] args) {
		SpringApplication.run(SalesRecordApplication.class, args);
	}
}
