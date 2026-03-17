package com.jbank.jbank.adapters.in.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositoDTO(

        @NotNull(message="O valor de deposito é obrigatório.")
        @Positive(message="O valor de deposito deve ser maior que zero.")
        BigDecimal valor
) { }
