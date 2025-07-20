package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.Cidade;
import com.thomazllr.algafood.domain.exception.CidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CidadeService {

    public static final String MSG_CIDADE_NAO_ENCONTRADA = "Cidade com ID: %d não encontrada";

    private final CidadeRepository repository;
    private final EstadoService estadoService;

    public Cidade salvar(Cidade cidade) {

        var estadoId = cidade.getEstado().getId();
        var estado = estadoService.buscarOuFalhar(estadoId);

        cidade.setEstado(estado);
        return repository.save(cidade);
    }

    public void remover(Long id) {
        var cidade = buscarOuFalhar(id);
        repository.delete(cidade);
    }

    public Cidade buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

}
