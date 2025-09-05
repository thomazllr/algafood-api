package com.thomazllr.algafood.api.assembler.formapagamento;

import com.thomazllr.algafood.api.model.input.formapagamento.FormaPagamentoInput;
import com.thomazllr.algafood.domain.entity.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormaPagamentoInputDisassembler {

    private final ModelMapper modelMapper;

    public FormaPagamento toEntity(FormaPagamentoInput input) {
        return modelMapper.map(input, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoInput input, FormaPagamento formaPagamento) {
        modelMapper.map(input, formaPagamento);
    }

}
