package com.thomazllr.algafood.api.assembler.restaurante;

import com.thomazllr.algafood.api.model.input.restaurante.RestauranteInput;
import com.thomazllr.algafood.domain.entity.Cozinha;
import com.thomazllr.algafood.domain.entity.Restaurante;
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

    public void copyToDomainObject(RestauranteInput input, Restaurante restaurante) {

        // Para evitar exception que o JPA acha que a gente tá tentando alterar um ID de uma cozinha para outro
        restaurante.setCozinha(new Cozinha());

        modelMapper.map(input, restaurante);
    }

}
