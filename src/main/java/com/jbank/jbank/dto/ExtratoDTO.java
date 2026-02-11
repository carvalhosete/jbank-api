package com.jbank.jbank.dto;

import com.jbank.jbank.model.enums.TipoTransacao;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExtratoDTO(
        TipoTransacao tipo,
        BigDecimal valor,
        LocalDateTime dataHora
) {}
