package com.thomazllr.algafood.domain.exception;

public class FotoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FotoNaoEncontradaException(String message) {
        super(message);
    }

    public FotoNaoEncontradaException(Long idRestaurante, Long idProduto) {
        this("Foto do Produto de Id %d e do Restaurante de Id %d não encontrada".formatted(idProduto, idRestaurante));
    }
}
