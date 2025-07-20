package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.Restaurante;
import com.thomazllr.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.thomazllr.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestauranteService {

    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Entidade de Restaurante de ID: %d não encontrada";

    private final RestauranteRepository repository;
    private final CozinhaService cozinhaService;

    public Restaurante salvar(Restaurante restaurante) {

        var cozinhaId = restaurante.getCozinha().getId();
        var cozinha = cozinhaService.buscarOuFalhar(cozinhaId);

        restaurante.setCozinha(cozinha);

        return repository.save(restaurante);
    }

    public void remover(Long id) {
        var restaurante = buscarOuFalhar(id);
        repository.delete(restaurante);
    }

    public Restaurante buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }


}
