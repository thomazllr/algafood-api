package com.thomazllr.algafood_api.infrastructure.repository;

import com.thomazllr.algafood_api.domain.Restaurante;
import com.thomazllr.algafood_api.domain.repository.RestauranteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> listar() {
        return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
    }

    @Override
    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        return manager.merge(restaurante);
    }

    @Override
    public Restaurante buscarPorId(Long id) {
        return manager.find(Restaurante.class, id);
    }

    @Override
    @Transactional
    public void remover(Restaurante restaurante) {
        restaurante = buscarPorId(restaurante.getId());
        manager.remove(restaurante);
    }
}
