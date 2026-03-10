package com.jbank.jbank.adapters.out.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class RabbitMQConfig {

    public static final String FILA_BACEN = "transferencia.bacen.queue";

    @Bean
    public Queue filaBacen(){
        return new Queue(FILA_BACEN, true);
    }
}
