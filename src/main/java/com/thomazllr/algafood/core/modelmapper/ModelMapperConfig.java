package com.thomazllr.algafood.core.modelmapper;

import com.thomazllr.algafood.api.model.EnderecoModel;
import com.thomazllr.algafood.domain.entity.Endereco;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        var modelMapper = new ModelMapper();

        TypeMap<Endereco, EnderecoModel> enderecoMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

        enderecoMap.<String>addMapping(src -> src.getCidade().getEstado().getNome(),
                (dest, value) -> dest.getCidade().setEstado(value));

        return modelMapper;
    }
}
