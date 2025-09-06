package com.thomazllr.algafood.api.assembler.produto;

import com.thomazllr.algafood.api.model.input.produto.ProdutoInput;
import com.thomazllr.algafood.domain.entity.Produto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoInputDisassembler {

    private final ModelMapper modelMapper;

    public Produto toEntity(ProdutoInput input) {
        return modelMapper.map(input, Produto.class);
    }

    public void copyToDomainObject(ProdutoInput input, Produto produto) {
        modelMapper.map(input, produto);
    }

}
