package com.thomazllr.algafood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends RuntimeException {

    protected EntidadeNaoEncontradaException(String message) {
        super(message);
    }
}
