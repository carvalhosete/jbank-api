package com.jbank.jbank.infra.exception;

import com.jbank.jbank.exception.ContaNaoEncontradaException;
import com.jbank.jbank.exception.SaqueInvalidoException;
import com.jbank.jbank.exception.SaldoInsuficienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ProblemDetail handleSaldoInsuficiente(SaldoInsuficienteException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());

        problemDetail.setTitle("Regra de Negócio Violada");
        problemDetail.setType(URI.create("https://jbank.com/erros/regra-de-negocio"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleErroGenerico(Exception e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro interno inesperado.");
        problemDetail.setTitle("Erro Interno");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        e.printStackTrace();

        return problemDetail;
    }

    @ExceptionHandler(SaqueInvalidoException.class)
    public ProblemDetail handleSaqueInvalido(SaqueInvalidoException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());

        problemDetail.setTitle("Saque Inválido");
        problemDetail.setType(URI.create("https://jbank.com/erros/saque-invalido"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }


    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ProblemDetail handleContaNaoEncontrada(ContaNaoEncontradaException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        problemDetail.setTitle("Recurso Não Encontrado");
        problemDetail.setType(URI.create("https://jbank.com/erros/recurso-nao-encontrado"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }
}
