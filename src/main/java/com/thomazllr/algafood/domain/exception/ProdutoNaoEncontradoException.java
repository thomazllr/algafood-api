package com.thomazllr.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradoException(Long id, Long idRestaurante) {
        this(String.format("Produto de ID: %d não encontrado para Restaurante de ID %d", id, idRestaurante));
    }
}
