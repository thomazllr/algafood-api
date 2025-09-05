package com.thomazllr.algafood.api.assembler.formapagamento;

import com.thomazllr.algafood.api.model.FormaPagamentoModel;
import com.thomazllr.algafood.domain.entity.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FormaPagamentoModelAssembler {

    private final ModelMapper modelMapper;

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
    }

    public List<FormaPagamentoModel> toCollectionModel(List<FormaPagamento> formaPagamentos) {
        return formaPagamentos.stream()
                .map(this::toModel)
                .toList();
    }
}
