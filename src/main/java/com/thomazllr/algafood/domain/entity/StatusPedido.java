package com.thomazllr.algafood.domain.entity;

import lombok.Getter;

import java.util.List;

@Getter
public enum StatusPedido {
    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Cancelado", CRIADO);

    final String descricao;
    private final List<StatusPedido> statusAnteriores;

    StatusPedido(String descricao, StatusPedido... statusAnteriores) {
        this.descricao = descricao;
        this.statusAnteriores = List.of(statusAnteriores);
    }

    public boolean podeAlterarPara(StatusPedido novoStatus) {
        return novoStatus.getStatusAnteriores().contains(this);
    }

    public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
        return !podeAlterarPara(novoStatus);
    }

}
