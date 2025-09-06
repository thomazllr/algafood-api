package com.thomazllr.algafood.core.modelmapper;

import com.thomazllr.algafood.api.model.EnderecoModel;
import com.thomazllr.algafood.api.model.input.itempedido.ItemPedidoInput;
import com.thomazllr.algafood.domain.entity.Endereco;
import com.thomazllr.algafood.domain.entity.ItemPedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        var modelMapper = new ModelMapper();

        modelMapper.
                createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        TypeMap<Endereco, EnderecoModel> enderecoMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

        enderecoMap.
                <String>addMapping(src -> src.getCidade().getEstado().getNome(),
                (dest, value) -> dest.getCidade().setEstado(value));

        return modelMapper;
    }
}
