package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.Restaurante;
import com.thomazllr.algafood.domain.exception.EntidadeEmUsoException;
import com.thomazllr.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.repository.CozinhaRepository;
import com.thomazllr.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CadastroRestauranteService {

    private final RestauranteRepository repository;
    private final CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {

        var cozinhaId = restaurante.getCozinha().getId();
        var cozinha = cozinhaRepository
                .findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Entidade de Cozinha de ID: %d não encontrada", cozinhaId)));

        restaurante.setCozinha(cozinha);

        return repository.save(restaurante);
    }

    public void remover(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Restaurante de ID: %d está em uso", id));
        }
    }

}
