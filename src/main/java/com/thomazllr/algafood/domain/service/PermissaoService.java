package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.Permissao;
import com.thomazllr.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.thomazllr.algafood.domain.repository.PermissaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissaoService {

    private final PermissaoRepository repository;
    private final GrupoService grupoService;

    public Permissao buscarOuFalhar(Long id) {
        return repository.findById(id).orElseThrow(() -> new PermissaoNaoEncontradaException(id));
    }

    public Permissao salvar(Permissao permissao) {
        return repository.save(permissao);
    }

    @Transactional
    public void associar(Long grupoId, Long permissaoId) {
        var grupo = grupoService.buscarOuFalhar(grupoId);
        var permissao = this.buscarOuFalhar(permissaoId);
        grupo.associarPermissao(permissao);
    }

    @Transactional
    public void desassociar(Long grupoId, Long permissaoId) {
        var grupo = grupoService.buscarOuFalhar(grupoId);
        var permissao = this.buscarOuFalhar(permissaoId);
        grupo.desassociarPermissao(permissao);

    }
}
