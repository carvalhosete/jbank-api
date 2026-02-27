package com.jbank.jbank.core.usecase;

import com.jbank.jbank.adapters.in.web.dto.ContaDTO;
import com.jbank.jbank.adapters.in.web.dto.ExtratoDTO;
import com.jbank.jbank.core.ports.out.ContaRepositoryPort;
import com.jbank.jbank.exception.ContaNaoEncontradaException;
import com.jbank.jbank.exception.SaqueInvalidoException;
import com.jbank.jbank.exception.SaldoInsuficienteException;
import com.jbank.jbank.core.domain.Conta;
import com.jbank.jbank.model.Transacao;
import com.jbank.jbank.model.enums.TipoTransacao;
import com.jbank.jbank.adapters.out.persistence.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaService {
    private final ContaRepositoryPort contaRepositoryPort;
    private final TransacaoRepository transacaoRepository;

    public ContaService(ContaRepositoryPort contaRepositoryPort, TransacaoRepository transacaoRepository) {
        this.contaRepositoryPort = contaRepositoryPort;
        this.transacaoRepository = transacaoRepository;
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

        Conta contaSalva = contaRepositoryPort.salvar(contaEntity);

        ContaDTO dtoResult = new ContaDTO();

        dtoResult.setId(contaSalva.getId());
        dtoResult.setSaldo(contaSalva.getSaldo());
        dtoResult.setTitular(contaSalva.getTitular());
        dtoResult.setAgencia(contaSalva.getAgencia());
        dtoResult.setNumero(contaSalva.getNumero());

        return dtoResult;
    }

    public ContaDTO buscarPorId(Long id){
        return contaRepositoryPort.buscarPorId(id)
                .map(ContaDTO::new)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada!"));
    }

    @Transactional
    public void depositar(Long id, BigDecimal valor){
        var conta = contaRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada!"));

        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new SaldoInsuficienteException("O valor do depósito deve ser maior que zero");
        }

        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepositoryPort.salvar(conta);

        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setValor(valor);
        transacao.setTipo(TipoTransacao.DEPOSITO);
        transacaoRepository.save(transacao);
    }

    @Transactional
    public void sacar(Long id, BigDecimal valor){
        var conta = contaRepositoryPort.buscarPorId(id)
                    .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada!"));

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new SaqueInvalidoException("O valor do saque deve ser maior que zero!");
        }

        if(conta.getSaldo().compareTo(valor) < 0){
            throw new SaqueInvalidoException("Saldo insuficiente!");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepositoryPort.salvar(conta);

        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setValor(valor);
        transacao.setTipo(TipoTransacao.SAQUE);
        transacaoRepository.save(transacao);
    }

    @Transactional
    public void transferir(Long idOrigem, Long idDestino, BigDecimal valor){
        if(idOrigem.equals(idDestino)){
            throw new SaldoInsuficienteException("Não é possível transferir para a mesma conta.");
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new SaldoInsuficienteException("O valor da transferência iválido.");
        }

        var contaOrigem = contaRepositoryPort.buscarPorId(idOrigem)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta origem não encontrada!"));

        var contaDestino = contaRepositoryPort.buscarPorId(idDestino)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta destino não encontrada!"));

        if(contaOrigem.getSaldo().compareTo(valor) < 0){
            throw new SaldoInsuficienteException("Saldo insuficiente para transferência.");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));
        contaRepositoryPort.salvar(contaOrigem);
        contaRepositoryPort.salvar(contaDestino);

        Transacao tOrigem = new Transacao();
        tOrigem.setConta(contaOrigem);
        tOrigem.setValor(valor);
        tOrigem.setTipo(TipoTransacao.TRANSFERENCIA_SAIDA);
        transacaoRepository.save(tOrigem);

        Transacao tDestino = new Transacao();
        tDestino.setConta(contaDestino);
        tDestino.setValor(valor);
        tDestino.setTipo(TipoTransacao.TRANSFERENCIA_ENTRADA);
        transacaoRepository.save(tDestino);

    }

    public List<ExtratoDTO> listarExtrato(Long idConta){
        return transacaoRepository.findByContaId(idConta)
                .stream()
                .map(t -> new ExtratoDTO(t.getTipo(),t.getValor(),t.getDataHora()))
                .toList();
    }
}
