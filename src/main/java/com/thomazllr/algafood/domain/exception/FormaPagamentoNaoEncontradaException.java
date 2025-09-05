package com.thomazllr.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradaException(Long id) {
        this(String.format("Forma de Pagamento de ID: %d não encontrada", id));
    }
}
