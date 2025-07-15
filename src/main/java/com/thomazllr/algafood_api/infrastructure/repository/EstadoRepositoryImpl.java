package com.thomazllr.algafood_api.infrastructure.repository;

import com.thomazllr.algafood_api.domain.Estado;
import com.thomazllr.algafood_api.domain.repository.EstadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Estado> listar() {
        return manager.createQuery("from Estado", Estado.class).getResultList();
    }

    @Override
    @Transactional
    public Estado salvar(Estado estado) {
        return manager.merge(estado);
    }

    @Override
    public Estado buscarPorId(Long id) {
        return manager.find(Estado.class, id);
    }

    @Override
    @Transactional
    public void remover(Estado estado) {
        estado = buscarPorId(estado.getId());
        manager.remove(estado);
    }
}
