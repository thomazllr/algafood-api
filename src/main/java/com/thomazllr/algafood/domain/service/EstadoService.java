package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.Estado;
import com.thomazllr.algafood.domain.exception.EntidadeEmUsoException;
import com.thomazllr.algafood.domain.exception.EstadoNaoEncontradoException;
import com.thomazllr.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EstadoService {

    public static final String MSG_ESTADO_EM_USO = "Estado de ID: %d está em uso";

    private final EstadoRepository repository;

    public Estado salvar(Estado estado) {
        return repository.save(estado);
    }

    public void remover(Long id) {
        var estado = buscarOuFalhar(id);
        try {
            repository.delete(estado);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, id)
            );
        }
    }

    public Estado buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }


}
