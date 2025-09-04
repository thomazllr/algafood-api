package com.thomazllr.algafood.api.assembler.estado;

import com.thomazllr.algafood.api.model.EstadoModel;
import com.thomazllr.algafood.domain.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EstadoModelAssembler {

    private final ModelMapper modelMapper;

    public EstadoModel toModel(Estado estado) {
        return modelMapper.map(estado, EstadoModel.class);
    }

    public List<EstadoModel> toCollectionModel(List<Estado> estados) {
        return estados.stream()
                .map(this::toModel)
                .toList();
    }
}
