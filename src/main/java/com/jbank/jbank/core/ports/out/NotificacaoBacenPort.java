package com.jbank.jbank.core.ports.out;

import com.jbank.jbank.core.domain.Transacao;

public interface NotificacaoBacenPort {
    void notificarTransferencia(Transacao transacao);
}
