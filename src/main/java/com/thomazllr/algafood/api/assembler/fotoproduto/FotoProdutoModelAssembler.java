package com.thomazllr.algafood.api.assembler.fotoproduto;

import com.thomazllr.algafood.api.model.FotoProdutoModel;
import com.thomazllr.algafood.domain.entity.FotoProduto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FotoProdutoModelAssembler {

    private final ModelMapper modelMapper;

    public FotoProdutoModel toModel(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto, FotoProdutoModel.class);
    }
}
