package com.jbank.jbank.adapters.out.persistence.repository;

import com.jbank.jbank.core.ports.out.ContaRepositoryPort;
import com.jbank.jbank.core.domain.Conta;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContaRepositoryAdapter implements ContaRepositoryPort{
        private final ContaRepository contaRepositoryJPA;

        public ContaRepositoryAdapter(ContaRepository contaRepositoryJPA){
            this.contaRepositoryJPA = contaRepositoryJPA;
        }

        @Override
        public Conta salvar(Conta contaPura){
            ContaEntity entity = new ContaEntity();
            entity.setId(contaPura.getId());
            entity.setTitular(contaPura.getTitular());
            entity.setAgencia(contaPura.getAgencia());
            entity.setNumero(contaPura.getNumero());
            entity.setSaldo(contaPura.getSaldo());

            ContaEntity entitySalva = contaRepositoryJPA.save(entity);

            Conta contaDeRetorno = new Conta();
            contaDeRetorno.setId(entitySalva.getId());
            contaDeRetorno.setTitular(entitySalva.getTitular());
            contaDeRetorno.setAgencia(entitySalva.getAgencia());
            contaDeRetorno.setNumero(entitySalva.getNumero());
            contaDeRetorno.setSaldo(entitySalva.getSaldo());

            return contaDeRetorno;
        }

        @Override
        public Optional<Conta> buscarPorId(Long id){
            Optional<ContaEntity> entityOptional = contaRepositoryJPA.findById(id);

            if(entityOptional.isEmpty()){
                return Optional.empty();
            }

            Conta contaPura = toDomain(entityOptional.get());

            return Optional.of(contaPura);
        }

        private ContaEntity toEntity(Conta dominio){
            ContaEntity entity = new ContaEntity();
            entity.setId(dominio.getId());
            entity.setTitular(dominio.getTitular());
            entity.setAgencia(dominio.getAgencia());
            entity.setNumero(dominio.getNumero());
            entity.setSaldo(dominio.getSaldo());
            return entity;
        }

        private Conta toDomain(ContaEntity entity){
            Conta dominio = new Conta();
            dominio.setId(entity.getId());
            dominio.setTitular(entity.getTitular());
            dominio.setAgencia(entity.getAgencia());
            dominio.setNumero(entity.getNumero());
            dominio.setSaldo(entity.getSaldo());
            return dominio;
        }
}
