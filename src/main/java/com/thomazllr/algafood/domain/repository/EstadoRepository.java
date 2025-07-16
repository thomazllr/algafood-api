package com.thomazllr.algafood.domain.repository;

import com.thomazllr.algafood.domain.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();
    Estado buscarPorId(Long id);
    Estado salvar(Estado estado);
    void remover(Long id);
}
