package com.thomazllr.algafood.api.assembler.cidade;

import com.thomazllr.algafood.api.model.CidadeModel;
import com.thomazllr.algafood.domain.entity.Cidade;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CidadeModelAssembler {

    private final ModelMapper modelMapper;

    public CidadeModel toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeModel.class);
    }

    public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(this::toModel)
                .toList();
    }
}
