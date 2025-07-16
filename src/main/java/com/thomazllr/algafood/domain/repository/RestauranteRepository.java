package com.thomazllr.algafood.domain.repository;

import com.thomazllr.algafood.domain.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> listar();
    Restaurante buscarPorId(Long id);
    Restaurante salvar(Restaurante restaurante);
    void remover(Long id);
}
