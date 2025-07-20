package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.Estado;
import com.thomazllr.algafood.domain.exception.EntidadeEmUsoException;
import com.thomazllr.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EstadoService {

    public static final String MSG_ESTADO_EM_USO = "Estado de ID: %d está em uso";
    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Estado de ID: %d não encontrado";

    private final EstadoRepository repository;

    public Estado salvar(Estado estado) {
        return repository.save(estado);
    }

    public void remover(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, id)
            );
        }
    }

    public Estado buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_ESTADO_NAO_ENCONTRADO, id)));
    }


}
