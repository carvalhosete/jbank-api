package com.jbank.jbank.repository;

import java.util.List;
import com.jbank.jbank.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByContaId(Long id);
}
