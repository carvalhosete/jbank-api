package com.jbank.jbank.adapters.in.queue;

import com.jbank.jbank.adapters.out.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.Queue;

@Component
public class TransferenciaBacenListener {

    @RabbitListener(queuesToDeclare = @Queue(RabbitMQConfig.FILA_BACEN))
    public void processarNotificacaoBacen(String mensagem){

        System.out.println("[RABBITMQ - CONSUMIDOR] Mensagem recebida da fila: " + mensagem);
        System.out.println("[RABBITMQ - CONSUMIDOR] Iniciando processamento pesado com o BACEN...");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("[RABBITMQ - CONSUMIDOR] BACEN notificado com sucesso! Mensagem processada e removida da fila.");
    }
}
