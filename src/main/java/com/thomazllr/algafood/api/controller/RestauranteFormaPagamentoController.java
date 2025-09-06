package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.formapagamento.FormaPagamentoModelAssembler;
import com.thomazllr.algafood.api.model.FormaPagamentoModel;
import com.thomazllr.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes/{id}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private final RestauranteService service;
    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @GetMapping
    public List<FormaPagamentoModel> listar(@PathVariable Long id) {
        var restaurante = service.buscarOuFalhar(id);
        var formasPagamento = restaurante.getFormasPagamento();
        return formaPagamentoModelAssembler.toCollectionModel(formasPagamento);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long id, @PathVariable Long formaPagamentoId) {
        service.associarFormaPagamento(id, formaPagamentoId);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long id, @PathVariable Long formaPagamentoId) {
        service.desassociarFormaPagamento(id, formaPagamentoId);
    }

}
