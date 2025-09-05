package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.Restaurante;
import com.thomazllr.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.thomazllr.algafood.domain.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestauranteService {

    private final RestauranteRepository repository;
    private final CozinhaService cozinhaService;

    @Transactional
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

    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);
        restaurante.inativar();
    }


}
