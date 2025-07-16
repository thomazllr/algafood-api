package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.Cidade;
import com.thomazllr.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.repository.CidadeRepository;
import com.thomazllr.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CadastroCidadeService {

    private final CidadeRepository repository;
    private final EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {

        var estadoId = cidade.getEstado().getId();
        var estado = estadoRepository.buscarPorId(estadoId);
        if(estado == null){
            throw new EntidadeNaoEncontradaException(String.format("Estado com ID: %d não encontrado",  estadoId));
        }
        cidade.setEstado(estado);
        return repository.salvar(cidade);
    }

    public void remover(Long id) {
        repository.remover(id);
    }

}
