package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.Cozinha;
import com.thomazllr.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.thomazllr.algafood.domain.exception.EntidadeEmUsoException;
import com.thomazllr.algafood.domain.repository.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CozinhaService {

    public static final String MSG_COZINHA_EM_USO = "Cozinha de ID: %d está em uso";

    private final CozinhaRepository repository;

    public Cozinha salvar(Cozinha cozinha) {
        return repository.save(cozinha);
    }

    public void remover(Long id) {
        Cozinha cozinha = buscarOuFalhar(id);
        try {
            repository.delete(cozinha);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));
        }
    }

    public Cozinha buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

}
