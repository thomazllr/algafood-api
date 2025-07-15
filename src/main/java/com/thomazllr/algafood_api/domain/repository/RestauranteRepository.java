package com.thomazllr.algafood_api.domain.repository;

import com.thomazllr.algafood_api.domain.Cozinha;
import com.thomazllr.algafood_api.domain.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> listar();
    Restaurante buscarPorId(Long id);
    Restaurante salvar(Restaurante restaurante);
    void remover(Restaurante restaurante);
}
