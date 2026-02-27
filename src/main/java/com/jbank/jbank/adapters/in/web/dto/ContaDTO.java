package com.jbank.jbank.adapters.in.web.dto;

import com.jbank.jbank.core.domain.Conta;

import java.math.BigDecimal;

public class ContaDTO {
    private Long id;
    private Integer agencia;
    private Integer numero;
    private BigDecimal saldo;
    private String titular;

    public ContaDTO(){
    }

    public ContaDTO(Conta conta){
        this.id = conta.getId();
        this.agencia = conta.getAgencia();
        this.numero = conta.getNumero();
        this.saldo = conta.getSaldo();
        this.titular = conta.getTitular();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }
}
