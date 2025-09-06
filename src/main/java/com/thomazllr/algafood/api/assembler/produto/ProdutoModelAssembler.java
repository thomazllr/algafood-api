package com.thomazllr.algafood.api.assembler.produto;

import com.thomazllr.algafood.api.model.ProdutoModel;
import com.thomazllr.algafood.domain.entity.Produto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProdutoModelAssembler {

    private final ModelMapper modelMapper;

    public ProdutoModel toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoModel.class);
    }

    public List<ProdutoModel> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toModel)
                .toList();
    }
}
