package com.jbank.jbank.core.ports.out;

import com.jbank.jbank.core.domain.Transacao;
import java.util.List;

public interface TransacaoRepositoryPort {
    Transacao salvar(Transacao transacao);

    List<Transacao> findByContaId(Long idConta);
}
