package com.thomazllr.algafood.domain.repository;

import com.thomazllr.algafood.domain.entity.Produto;
import com.thomazllr.algafood.domain.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findProdutosByRestaurante(Restaurante restaurante);

    @Query("from Produto where restaurante.id = :restaurante and id = :produto")
    Optional<Produto> findById(@Param("restaurante") Long restauranteId,
                               @Param("produto") Long produtoId);}
