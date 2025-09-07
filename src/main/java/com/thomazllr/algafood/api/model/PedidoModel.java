package com.thomazllr.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoModel {

    private String codigo;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private List<ItemPedidoModel> itens;
    private RestauranteResumidoModel restaurante;
    private FormaPagamentoModel formaPagamento;
    private UsuarioModel cliente;
    private EnderecoModel endereco;

}
