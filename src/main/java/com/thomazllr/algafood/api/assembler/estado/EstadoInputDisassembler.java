package com.thomazllr.algafood.api.assembler.estado;

import com.thomazllr.algafood.api.model.input.estado.EstadoInput;
import com.thomazllr.algafood.domain.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstadoInputDisassembler {

    private final ModelMapper modelMapper;

    public Estado toEntity(EstadoInput input) {
        return modelMapper.map(input, Estado.class);
    }

    public void copyToDomainObject(EstadoInput input, Estado estado) {
        modelMapper.map(input, estado);
    }

}
