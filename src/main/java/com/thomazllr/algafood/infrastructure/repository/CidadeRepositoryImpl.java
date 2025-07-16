package com.thomazllr.algafood.infrastructure.repository;

import com.thomazllr.algafood.domain.Cidade;
import com.thomazllr.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.repository.CidadeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cidade> listar() {
        return manager.createQuery("from Cidade", Cidade.class).getResultList();
    }

    @Override
    @Transactional
    public Cidade salvar(Cidade cidade) {
        return manager.merge(cidade);
    }

    @Override
    public Cidade buscarPorId(Long id) {
        return manager.find(Cidade.class, id);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        var cidade = buscarPorId(id);
        if (cidade == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Cidade de ID: %d não encontrada", id)
            );
        }
        manager.remove(cidade);
    }
}
