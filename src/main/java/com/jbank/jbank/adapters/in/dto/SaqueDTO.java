package com.jbank.jbank.adapters.in.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record SaqueDTO(

        @NotNull(message="O valor de saque é obrigatório.")
        @Positive(message="O valor de saque deve ser maior que zero.")
        BigDecimal valor
) { }
