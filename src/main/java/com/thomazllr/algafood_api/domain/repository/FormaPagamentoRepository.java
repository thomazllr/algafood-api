package com.thomazllr.algafood_api.domain.repository;

import com.thomazllr.algafood_api.domain.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> listar();
    FormaPagamento buscarPorId(Long id);
    FormaPagamento salvar(FormaPagamento formaPagamento);
    void remover(FormaPagamento formaPagamento);
}
