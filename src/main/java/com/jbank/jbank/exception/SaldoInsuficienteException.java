package com.jbank.jbank.exception;

public class SaldoInsuficienteException extends RuntimeException{

    public SaldoInsuficienteException(String mensagem){
        super(mensagem);
    }
}
