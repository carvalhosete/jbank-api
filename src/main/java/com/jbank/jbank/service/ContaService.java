package com.jbank.jbank.service;

import com.jbank.jbank.dto.ContaDTO;
import com.jbank.jbank.exception.ContaNaoEncontradaException;
import com.jbank.jbank.exception.SaldoInsuficienteException;
import com.jbank.jbank.model.Conta;
import com.jbank.jbank.repository.ContaRepository;
import org.springframework.stereotype.Service;

@Service
public class ContaService {
    private final ContaRepository repository;

    public ContaService(ContaRepository repository) {
        this.repository = repository;
    }

    public ContaDTO salvar(ContaDTO dto){

        if(dto.getSaldo() < 0){
            throw new SaldoInsuficienteException("O saldo inicial não pode ser negativo!");
        }
        Conta contaEntity = new Conta();

        contaEntity.setSaldo(dto.getSaldo());
        contaEntity.setTitular(dto.getTitular());
        contaEntity.setAgencia(dto.getAgencia());
        contaEntity.setNumero(dto.getNumero());

        Conta contaSalva = repository.save(contaEntity);

        ContaDTO dtoResult = new ContaDTO();

        dtoResult.setId(contaSalva.getId());
        dtoResult.setSaldo(contaSalva.getSaldo());
        dtoResult.setTitular(contaSalva.getTitular());
        dtoResult.setAgencia(contaSalva.getAgencia());
        dtoResult.setNumero(contaSalva.getNumero());

        return dtoResult;
    }

    public ContaDTO buscarPorId(Long id){
        return repository.findById(id)
                .map(ContaDTO::new)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada!"));
    }
}
