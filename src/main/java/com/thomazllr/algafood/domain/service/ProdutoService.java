package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.Produto;
import com.thomazllr.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.thomazllr.algafood.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    private final RestauranteService restauranteService;

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {

        restauranteService.buscarOuFalhar(restauranteId);

        return repository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));

    }


}
