package com.thomazllr.algafood.domain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class VendaDiaria {

    private LocalDate data;
    private Long totalVendas;
    private BigDecimal totalFaturado;

}
