package com.thomazllr.algafood.domain.repository;

import com.thomazllr.algafood.domain.entity.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto foto);

    void delete(FotoProduto foto);

}
