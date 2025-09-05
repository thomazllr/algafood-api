package com.thomazllr.algafood.api.assembler.usuario;

import com.thomazllr.algafood.api.model.input.usuario.UsuarioInput;
import com.thomazllr.algafood.api.model.input.usuario.UsuarioUpdateInput;
import com.thomazllr.algafood.domain.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioInputDisassembler {

    private final ModelMapper modelMapper;

    public Usuario toEntity(UsuarioInput input) {
        return modelMapper.map(input, Usuario.class);
    }

    public void copyToDomainObject(UsuarioUpdateInput input, Usuario usuario) {
        modelMapper.map(input, usuario);
    }

}
