package com.thomazllr.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }

    public PermissaoNaoEncontradaException(Long id) {
        this(String.format("Permissão de ID: %d não encontrada", id));
    }
}
