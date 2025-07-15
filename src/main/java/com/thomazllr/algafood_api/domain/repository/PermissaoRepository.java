package com.thomazllr.algafood_api.domain.repository;

import com.thomazllr.algafood_api.domain.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> listar();
    Permissao buscarPorId(Long id);
    Permissao salvar(Permissao permissao);
    void remover(Permissao permissao);
}
