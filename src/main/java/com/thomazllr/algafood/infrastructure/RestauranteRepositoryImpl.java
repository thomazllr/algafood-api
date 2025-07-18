package com.thomazllr.algafood.infrastructure;

import com.thomazllr.algafood.domain.Restaurante;
import com.thomazllr.algafood.domain.repository.RestauranteRepository;
import com.thomazllr.algafood.domain.repository.RestauranteRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.thomazllr.algafood.infrastructure.spec.RestauranteSpecs.comFreteGratis;
import static com.thomazllr.algafood.infrastructure.spec.RestauranteSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    private final RestauranteRepository repository;

    @Lazy
    public RestauranteRepositoryImpl(RestauranteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

        Root<Restaurante> root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasLength(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return repository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }
}
