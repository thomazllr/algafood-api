package com.thomazllr.algafood.domain.event;

import com.thomazllr.algafood.domain.entity.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {
    private Pedido pedido;
}
