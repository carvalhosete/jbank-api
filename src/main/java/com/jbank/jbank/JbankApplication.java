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
        ContaEntity c = new ContaEntity();

        c.setAgencia(1714);
        c.setNumero(2982);
        c.setSaldo(BigDecimal.valueOf(5000.00));
        c.setTitular("Leonardo");

        repository.save(c);

        ContaEntity c2 = new ContaEntity();

        c2.setAgencia(9984);
        c2.setNumero(9654);
        c2.setSaldo(BigDecimal.valueOf(10000.00));
        c2.setTitular("Carvalho");

        repository.save(c2);
    }
}
