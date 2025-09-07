package com.thomazllr.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JsonFilter("pedidoFilter")
@Getter
@Setter
public class PedidoResumoModel {

    private String codigo;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private OffsetDateTime dataCriacao;
    private RestauranteResumidoModel restaurante;
    private UsuarioModel cliente;
}
