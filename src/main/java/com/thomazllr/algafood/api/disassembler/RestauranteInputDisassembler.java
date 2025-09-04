package com.thomazllr.algafood.api.disassembler;

import com.thomazllr.algafood.api.model.input.RestauranteInput;
import com.thomazllr.algafood.domain.Restaurante;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestauranteInputDisassembler {

    private final ModelMapper modelMapper;

    public Restaurante toEntity(RestauranteInput input) {
        return modelMapper.map(input, Restaurante.class);
    }

}
