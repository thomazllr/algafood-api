package com.thomazllr.algafood.infrastructure.spec;

import com.thomazllr.algafood.domain.entity.Pedido;
import com.thomazllr.algafood.domain.repository.filter.PedidoFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filter) {
        return (root, query, builder) -> {
            var predicates = new ArrayList<Predicate>();

            root.fetch("restaurante").fetch("cozinha");
            root.fetch("cliente");

            if (filter.getClienteId() != null) {
                predicates.add(builder.equal(root.get("cliente").get("id"), filter.getClienteId()));
            }

            if (filter.getRestauranteId() != null) {
                predicates.add(builder.equal(root.get("restaurante").get("id"), filter.getRestauranteId()));
            }

            if (filter.getDataCriacaoInicio() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
            }

            if (filter.getDataCriacaoFim() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
