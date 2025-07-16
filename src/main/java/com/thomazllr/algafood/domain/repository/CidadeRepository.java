package com.thomazllr.algafood.domain.repository;

import com.thomazllr.algafood.domain.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> listar();

    Cidade buscarPorId(Long id);

    Cidade salvar(Cidade cidade);
    void remover(Long cidade);
}
