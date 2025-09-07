package com.thomazllr.algafood.api.model.input.pedido;

import com.thomazllr.algafood.api.model.input.cliente.ClienteIdInput;
import com.thomazllr.algafood.api.model.input.endereco.EnderecoInput;
import com.thomazllr.algafood.api.model.input.formapagamento.FormaPagamentoIdInput;
import com.thomazllr.algafood.api.model.input.itempedido.ItemPedidoInput;
import com.thomazllr.algafood.api.model.input.restaurante.RestauranteIdInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoInput {

    @Valid
    @NotNull
    private RestauranteIdInput restaurante;

    @Valid
    @NotNull
    private FormaPagamentoIdInput formaPagamento;

    @Valid
    @NotNull
    private ClienteIdInput cliente;

    @Valid
    @NotNull
    private EnderecoInput enderecoEntrega;

    @NotNull
    @Valid
    @Size(min = 1)
    List<ItemPedidoInput> itens;

}
