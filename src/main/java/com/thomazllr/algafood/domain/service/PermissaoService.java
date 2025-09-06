package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.Permissao;
import com.thomazllr.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.thomazllr.algafood.domain.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissaoService {

    private final PermissaoRepository repository;

    public Permissao buscarOuFalhar(Long id) {
        return repository.findById(id).orElseThrow(() -> new PermissaoNaoEncontradaException(id));
    }

    public Permissao salvar(Permissao permissao) {
        return repository.save(permissao);
    }


}
