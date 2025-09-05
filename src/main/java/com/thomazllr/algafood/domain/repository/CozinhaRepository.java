package com.thomazllr.algafood.domain.repository;

import com.thomazllr.algafood.domain.entity.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    List<Cozinha> findByNome(String nome);
}
