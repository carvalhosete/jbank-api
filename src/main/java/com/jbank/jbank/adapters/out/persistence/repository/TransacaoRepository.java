package com.jbank.jbank.adapters.out.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, Long> {
    Page<TransacaoEntity> findByContaId(Long contaId, Pageable pageable);
}
