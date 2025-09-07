package com.thomazllr.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String id) {
        super(String.format("Pedido de ID: %s não encontrado", id));
    }
}
