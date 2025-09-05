package com.thomazllr.algafood.domain.repository;

import com.thomazllr.algafood.domain.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestauranteRepository extends
        CustomJpaRepository<Restaurante, Long>,
        RestauranteRepositoryQueries,
        JpaSpecificationExecutor<Restaurante> {

    @Query("from Restaurante r join fetch r.cozinha")
    List<Restaurante> findAll();

    @Query("from Restaurante where nome like %:nome% and cozinha.id = :cozinhaId")
    List<Restaurante> consultarPorNome(String nome, Long cozinhaId);

}
