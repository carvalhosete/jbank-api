package com.jbank.jbank.adapters.out.notificacao;

import com.jbank.jbank.core.domain.Transacao;
import com.jbank.jbank.core.ports.out.NotificacaoBacenPort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoBacenAdapter implements NotificacaoBacenPort {

    @Async
    @Override
    public void notificarTransferencia(Transacao transacao){
        System.out.println("[ASSÍNCRONO] Thread: " + Thread.currentThread().getName() + " - Iniciando notificação BACEN...");

        try{
            Thread.sleep(5000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("[ASSÍNCRONO] Thread: " + Thread.currentThread().getName() + " - BACEN notificado com sucesso!");
    }
}
