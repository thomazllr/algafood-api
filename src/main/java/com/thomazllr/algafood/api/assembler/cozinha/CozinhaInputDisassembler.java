package com.thomazllr.algafood.api.assembler.cozinha;

import com.thomazllr.algafood.api.model.input.cozinha.CozinhaInput;
import com.thomazllr.algafood.domain.Cozinha;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CozinhaInputDisassembler {

    private final ModelMapper modelMapper;

    public Cozinha toEntity(CozinhaInput input) {
        return modelMapper.map(input, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInput input, Cozinha cozinha) {
        modelMapper.map(input, cozinha);
    }

}
