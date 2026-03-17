package com.jbank.jbank.core.usecase;

import com.jbank.jbank.core.domain.Conta;
import com.jbank.jbank.core.domain.Transacao;
import com.jbank.jbank.core.ports.out.ContaRepositoryPort;
import com.jbank.jbank.core.ports.out.NotificacaoBacenPort;
import com.jbank.jbank.core.ports.out.TransacaoRepositoryPort;
import com.jbank.jbank.exception.SaldoInsuficienteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @Mock
    private ContaRepositoryPort contaRepositoryPort;

    @Mock
    private TransacaoRepositoryPort transacaoRepositoryPort;

    @Mock
    private NotificacaoBacenPort notificacaoBacenPort;

    @InjectMocks
    private ContaService contaService;

    @Test
    @DisplayName("Deve realizar transferencia com sucesso quando dados forem validos")
    void deveTransferirComSucesso() {
        // GIVEN
        Long idOrigem = 1L;
        Long idDestino = 2L;
        BigDecimal valor = new BigDecimal("100.00");

        Conta contaOrigem = new Conta();
        contaOrigem.setId(idOrigem);
        contaOrigem.setSaldo(new BigDecimal("1000.00"));

        Conta contaDestino = new Conta();
        contaDestino.setId(idDestino);
        contaDestino.setSaldo(new BigDecimal("500.00"));

        Transacao transacaoFake = new Transacao(); // Criamos para o mock da notificacao

        when(contaRepositoryPort.buscarPorId(idOrigem)).thenReturn(Optional.of(contaOrigem));
        when(contaRepositoryPort.buscarPorId(idDestino)).thenReturn(Optional.of(contaDestino));
        // O seu service precisa do retorno do salvar da transacao para notificar o Bacen
        when(transacaoRepositoryPort.salvar(any(Transacao.class))).thenReturn(transacaoFake);

        // WHEN
        assertDoesNotThrow(() -> contaService.transferir(idOrigem, idDestino, valor));

        // THEN
        assertEquals(new BigDecimal("900.00"), contaOrigem.getSaldo());
        assertEquals(new BigDecimal("600.00"), contaDestino.getSaldo());

        verify(contaRepositoryPort, times(2)).salvar(any(Conta.class));
        verify(transacaoRepositoryPort, times(2)).salvar(any(Transacao.class));
        verify(notificacaoBacenPort).notificarTransferencia(any(Transacao.class));
    }

    @Test
    @DisplayName("Deve lancar excecao quando saldo for insuficiente")
    void deveLancarErroSaldoInsuficiente() {
        // GIVEN
        Long idOrigem = 1L;
        Long idDestino = 2L;

        Conta contaOrigem = new Conta();
        contaOrigem.setId(idOrigem);
        contaOrigem.setSaldo(new BigDecimal("50.00"));

        Conta contaDestino = new Conta();
        contaDestino.setId(idDestino);

        when(contaRepositoryPort.buscarPorId(idOrigem)).thenReturn(Optional.of(contaOrigem));
        when(contaRepositoryPort.buscarPorId(idDestino)).thenReturn(Optional.of(contaDestino));

        // WHEN & THEN
        assertThrows(SaldoInsuficienteException.class, () ->
                contaService.transferir(idOrigem, idDestino, new BigDecimal("100.00"))
        );

        verify(transacaoRepositoryPort, never()).salvar(any());
    }
}