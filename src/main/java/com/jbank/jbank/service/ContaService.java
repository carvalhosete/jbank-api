package com.jbank.jbank.service;

import com.jbank.jbank.dto.ContaDTO;
import com.jbank.jbank.exception.ContaNaoEncontradaException;
import com.jbank.jbank.exception.SaqueInvalidoException;
import com.jbank.jbank.exception.SaldoInsuficienteException;
import com.jbank.jbank.model.Conta;
import com.jbank.jbank.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class ContaService {
    private final ContaRepository repository;

    public ContaService(ContaRepository repository) {
        this.repository = repository;
    }

    public ContaDTO salvar(ContaDTO dto){

        if(dto.getSaldo().compareTo(BigDecimal.ZERO) < 0){
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

    public void depositar(Long id, BigDecimal valor){
        var conta = repository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada!"));

        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new SaldoInsuficienteException("O valor do depósito deve ser maior que zero");
        }

        conta.setSaldo(conta.getSaldo().add(valor));

        repository.save(conta);
    }

    @Transactional
    public void sacar(Long id, BigDecimal valor){
        var conta = repository.findById(id)
                    .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada!"));

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new SaqueInvalidoException("O valor do saque deve ser maior que zero!");
        }

        if(conta.getSaldo().compareTo(valor) < 0){
            throw new SaqueInvalidoException("Saldo insuficiente!");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));

        repository.save(conta);
    }

    @Transactional
    public void transferir(Long idOrigem, Long idDestino, BigDecimal valor){
        if(idOrigem.equals(idDestino)){
            throw new SaldoInsuficienteException("Não é possível transferir para a mesma conta.");
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new SaldoInsuficienteException("O valor da transferência iválido.");
        }

        var contaOrigem = repository.findById(idOrigem)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta origem não encontrada!"));

        var contaDestino = repository.findById(idDestino)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta destino não encontrada!"));

        if(contaOrigem.getSaldo().compareTo(valor) < 0){
            throw new SaldoInsuficienteException("Saldo insuficiente para transferência.");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        repository.save(contaOrigem);
        repository.save(contaDestino);

    }
}
