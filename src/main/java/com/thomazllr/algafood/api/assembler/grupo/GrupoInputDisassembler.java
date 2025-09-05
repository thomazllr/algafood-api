package com.thomazllr.algafood.api.assembler.grupo;

import com.thomazllr.algafood.api.model.input.grupo.GrupoInput;
import com.thomazllr.algafood.domain.entity.Grupo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrupoInputDisassembler {

    private final ModelMapper modelMapper;

    public Grupo toEntity(GrupoInput input) {
        return modelMapper.map(input, Grupo.class);
    }

    public void copyToDomainObject(GrupoInput input, Grupo grupo) {
        modelMapper.map(input, grupo);
    }

}
