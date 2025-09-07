package com.thomazllr.algafood.infrastructure.repository.spec;

import com.thomazllr.algafood.domain.entity.Pedido;
import com.thomazllr.algafood.domain.filter.PedidoFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filter) {
        return (root, query, builder) -> {

            if (Pedido.class.equals(query.getResultType())) {
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
            }

            var predicates = new ArrayList<Predicate>();

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
