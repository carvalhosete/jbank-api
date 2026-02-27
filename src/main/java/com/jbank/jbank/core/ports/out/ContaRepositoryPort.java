package com.jbank.jbank.core.ports.out;

import com.jbank.jbank.core.domain.Conta;
import java.util.Optional;

public interface ContaRepositoryPort {
    Conta salvar(Conta conta);

    Optional<Conta> buscarPorId(Long id);
}
