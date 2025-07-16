package com.thomazllr.algafood.domain.repository;

import com.thomazllr.algafood.domain.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> listar();
    Permissao buscarPorId(Long id);
    Permissao salvar(Permissao permissao);
    void remover(Permissao permissao);
}
