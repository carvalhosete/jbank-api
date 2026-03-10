package com.jbank.jbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class JbankApplication {
	public static void main(String[] args) {
        SpringApplication.run(JbankApplication.class, args);
	}
}
