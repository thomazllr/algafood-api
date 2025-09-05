package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.Grupo;
import com.thomazllr.algafood.domain.exception.EntidadeEmUsoException;
import com.thomazllr.algafood.domain.exception.GrupoNaoEncontradoException;
import com.thomazllr.algafood.domain.repository.GrupoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrupoService {

    public static final String MSG_GRUPO_EM_USO = "Grupo de ID: %d está em uso";

    private final GrupoRepository repository;

    public Grupo salvar(Grupo grupo) {
        return repository.save(grupo);
    }

    public void remover(Long id) {
        var grupo = buscarOuFalhar(id);
        try {
            repository.delete(grupo);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_GRUPO_EM_USO, id)
            );
        }
    }

    public Grupo buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

}
