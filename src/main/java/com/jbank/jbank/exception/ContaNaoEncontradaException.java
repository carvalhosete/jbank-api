package com.jbank.jbank.exception;

public class ContaNaoEncontradaException  extends RuntimeException {
    public ContaNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
