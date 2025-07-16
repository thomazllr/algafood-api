package com.thomazllr.algafood.domain.repository;

import com.thomazllr.algafood.domain.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> listar();
    Cozinha buscarPorId(Long id);
    Cozinha salvar(Cozinha cozinha);
    void remover(Long cozinha);
}
