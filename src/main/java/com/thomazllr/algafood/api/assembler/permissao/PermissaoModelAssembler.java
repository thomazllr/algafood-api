package com.thomazllr.algafood.api.assembler.permissao;

import com.thomazllr.algafood.api.model.PermissaoModel;
import com.thomazllr.algafood.domain.entity.Permissao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissaoModelAssembler {

    private final ModelMapper modelMapper;

    public PermissaoModel toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    public List<PermissaoModel> toCollectionModel(Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(this::toModel)
                .toList();
    }
}
