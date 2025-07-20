package com.thomazllr.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeEmUsoException {

    public EstadoNaoEncontradoException(String message) {
        super(message);
    }

    public EstadoNaoEncontradoException(Long id) {
        this(String.format("Estado de ID: %d não encontrado", id));
    }
}
