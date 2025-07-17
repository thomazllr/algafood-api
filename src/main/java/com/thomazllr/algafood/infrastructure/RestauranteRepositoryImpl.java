package com.thomazllr.algafood.infrastructure;

import com.thomazllr.algafood.domain.Restaurante;
import com.thomazllr.algafood.domain.repository.RestauranteRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        var jpql = "from Restaurante where nome like :nome and taxaFrete between :taxaFreteInicial and :taxaFreteFinal";

        return entityManager.createQuery(jpql, Restaurante.class)
                .setParameter("nome", "¨%d" + nome + "%d")
                .setParameter("taxaFreteInicial", taxaFreteFinal)
                .setParameter("taxaFreteFinal", taxaFreteFinal)
                .getResultList();

    }
}
