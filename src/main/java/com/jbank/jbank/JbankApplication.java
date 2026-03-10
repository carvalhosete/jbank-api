package com.jbank.jbank;

import com.jbank.jbank.adapters.out.persistence.repository.ContaEntity;
import com.jbank.jbank.adapters.out.persistence.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class JbankApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JbankApplication.class, args);
	}

    @Autowired
    private ContaRepository repository;

    @Override
    public void run(String... args) throws Exception {
    }
}
