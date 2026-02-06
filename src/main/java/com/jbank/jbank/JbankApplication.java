package com.jbank.jbank;

import com.jbank.jbank.model.Conta;
import com.jbank.jbank.repository.ContaRepository;
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
        Conta c = new Conta();

        c.setAgencia(1714);
        c.setNumero(2982);
        c.setSaldo(BigDecimal.valueOf(5000.00));
        c.setTitular("Leonardo Carvalho");

        repository.save(c);
    }
}
