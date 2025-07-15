package com.thomazllr.algafood_api.domain.repository;

import com.thomazllr.algafood_api.domain.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();
    Estado buscarPorId(Long id);
    Estado salvar(Estado estado);
    void remover(Estado estado);
}
