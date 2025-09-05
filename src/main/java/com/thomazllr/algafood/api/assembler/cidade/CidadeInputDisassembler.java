package com.thomazllr.algafood.api.assembler.cidade;

import com.thomazllr.algafood.api.model.input.cidade.CidadeInput;
import com.thomazllr.algafood.domain.entity.Cidade;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CidadeInputDisassembler {

    private final ModelMapper modelMapper;

    public Cidade toEntity(CidadeInput input) {
        return modelMapper.map(input, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput input, Cidade cidade) {
        modelMapper.map(input, cidade);
    }

}
