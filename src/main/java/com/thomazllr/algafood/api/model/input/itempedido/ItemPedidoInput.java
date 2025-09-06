package com.thomazllr.algafood.api.model.input.itempedido;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

    @PositiveOrZero
    @NotNull
    private Integer quantidade;

    private String observacao;

    @Valid
    @NotNull
    private Long produtoId;
}
