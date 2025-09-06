package com.thomazllr.algafood.api.assembler.pedido;

import com.thomazllr.algafood.api.model.input.pedido.PedidoInput;
import com.thomazllr.algafood.domain.entity.Pedido;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoInputDisassembler {

    private final ModelMapper modelMapper;

    public Pedido toEntity(PedidoInput input) {
        return modelMapper.map(input, Pedido.class);
    }
}
