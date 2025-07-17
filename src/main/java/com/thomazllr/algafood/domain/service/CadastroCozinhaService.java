package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.Cozinha;
import com.thomazllr.algafood.domain.exception.EntidadeEmUsoException;
import com.thomazllr.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.repository.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CadastroCozinhaService {

    private final CozinhaRepository repository;

    public Cozinha salvar(Cozinha cozinha) {
        return repository.save(cozinha);
    }

    public void remover(Long id) {
        Cozinha cozinha = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Cozinha de ID: %d não encontrada", id)));

        try {
            repository.delete(cozinha);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cozinha de ID: %d está em uso", id));
        }
    }

}
