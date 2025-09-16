package com.thomazllr.algafood.infrastructure.service.query;

import com.thomazllr.algafood.domain.entity.Pedido;
import com.thomazllr.algafood.domain.entity.dto.VendaDiaria;
import com.thomazllr.algafood.domain.filter.VendaDiariaFilter;
import com.thomazllr.algafood.domain.service.VendaQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {

        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        List<Predicate> predicates = new ArrayList<>();

        var functionConvertTzDataCriacao = builder.function("convert_tz", LocalDate.class,
                root.get("dataCriacao"),
                builder.literal("+00:00"),
                builder.literal(timeOffset));

        var functionDateDataCriacao = builder.function("date", LocalDate.class, functionConvertTzDataCriacao);

        var selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        if (filtro.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
        }

        if (filtro.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
        }

        if (filtro.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
        }

        predicates.add(root.get("status").in("CONFIRMADO", "ENTREGUE"));
        query.select(selection);
        query.groupBy(functionDateDataCriacao);
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
