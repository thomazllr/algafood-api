package com.thomazllr.algafood.domain.repository;

import com.thomazllr.algafood.domain.entity.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    @Query("select max(f.dataAtualizacao) from FormaPagamento f")
    OffsetDateTime getUltimaDataAtualizacao();

}
