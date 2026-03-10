package com.jbank.jbank.adapters.out.notificacao;

import com.jbank.jbank.adapters.out.config.RabbitMQConfig;
import com.jbank.jbank.core.domain.Transacao;
import com.jbank.jbank.core.ports.out.NotificacaoBacenPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoBacenAdapter implements NotificacaoBacenPort {

    private final RabbitTemplate rabbitTemplate;

    public NotificacaoBacenAdapter(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void notificarTransferencia(Transacao transacao){
        String mensagem = "Notificar BACEN sobre transferencia. ID da Transação: " + transacao.getId();

       rabbitTemplate.convertAndSend(RabbitMQConfig.FILA_BACEN, mensagem);

        System.out.println("[RABBITMQ] Mensagem enviada para fila com sucesso!");
    }
}
