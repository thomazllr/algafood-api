package com.thomazllr.algafood_api.domain.repository;

import com.thomazllr.algafood_api.domain.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> listar();
    Cozinha buscarPorId(Long id);
    Cozinha salvar(Cozinha cozinha);
    void remover(Cozinha cozinha);
}
