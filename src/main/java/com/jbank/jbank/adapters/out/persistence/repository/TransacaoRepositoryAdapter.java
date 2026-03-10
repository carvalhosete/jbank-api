package com.jbank.jbank.adapters.out.persistence.repository;
import com.jbank.jbank.core.domain.Conta;
import com.jbank.jbank.core.domain.Transacao;
import com.jbank.jbank.core.ports.out.TransacaoRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransacaoRepositoryAdapter implements TransacaoRepositoryPort {
    private final TransacaoRepository transacaoRepositoryJPA;

    public TransacaoRepositoryAdapter(TransacaoRepository transacaoRepositoryJPA){
        this.transacaoRepositoryJPA = transacaoRepositoryJPA;
    }

    @Override
    public Transacao salvar(Transacao transacaoPura) {
        TransacaoEntity entity = new TransacaoEntity();
        entity.setId(transacaoPura.getId());
        entity.setTipo(transacaoPura.getTipo());
        entity.setValor(transacaoPura.getValor());
        entity.setDataHora(transacaoPura.getDataHora());
        entity.setConta(toEntity(transacaoPura.getConta()));

        TransacaoEntity entitySalva = transacaoRepositoryJPA.save(entity);

        Transacao transacaoRetorno = new Transacao();
        transacaoRetorno.setId(entitySalva.getId());
        transacaoRetorno.setTipo(entitySalva.getTipo());
        transacaoRetorno.setValor(entitySalva.getValor());
        transacaoRetorno.setDataHora(entitySalva.getDataHora());
        transacaoRetorno.setConta(toDomain(entitySalva.getConta()));

        return transacaoRetorno;
    }

    @Override
    public List<Transacao> findByContaId(Long idConta, int numeroPagina, int tamanhoPagina) {

        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(numeroPagina, tamanhoPagina);
        org.springframework.data.domain.Page<TransacaoEntity> paginaDeEntidades = transacaoRepositoryJPA.findByContaId(idConta, pageable);

        return paginaDeEntidades.stream()
                .map(this::toTransacaoDomain)
                .toList();
    }

    private ContaEntity toEntity(Conta dominio){
        if (dominio == null) return null;
        ContaEntity entity = new ContaEntity();
        entity.setId(dominio.getId());
        entity.setTitular(dominio.getTitular());
        entity.setAgencia(dominio.getAgencia());
        entity.setNumero(dominio.getNumero());
        entity.setSaldo(dominio.getSaldo());
        return entity;
    }

    private Conta toDomain(ContaEntity entity){
        if (entity == null) return null;
        Conta dominio = new Conta();
        dominio.setId(entity.getId());
        dominio.setTitular(entity.getTitular());
        dominio.setAgencia(entity.getAgencia());
        dominio.setNumero(entity.getNumero());
        dominio.setSaldo(entity.getSaldo());
        return dominio;
    }

    private Transacao toTransacaoDomain(TransacaoEntity entitySalva){
        Transacao transacaoRetorno = new Transacao();
        transacaoRetorno.setId(entitySalva.getId());
        transacaoRetorno.setTipo(entitySalva.getTipo());
        transacaoRetorno.setValor(entitySalva.getValor());
        transacaoRetorno.setDataHora(entitySalva.getDataHora());
        transacaoRetorno.setConta(toDomain(entitySalva.getConta()));

        return transacaoRetorno;
    }
}
