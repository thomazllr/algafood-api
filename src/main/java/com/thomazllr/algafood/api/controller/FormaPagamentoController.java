package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.formapagamento.FormaPagamentoInputDisassembler;
import com.thomazllr.algafood.api.assembler.formapagamento.FormaPagamentoModelAssembler;
import com.thomazllr.algafood.api.model.FormaPagamentoModel;
import com.thomazllr.algafood.api.model.input.formapagamento.FormaPagamentoInput;
import com.thomazllr.algafood.domain.repository.FormaPagamentoRepository;
import com.thomazllr.algafood.domain.service.FormaPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    private final FormaPagamentoRepository repository;
    private final FormaPagamentoService service;

    private final FormaPagamentoModelAssembler assembler;
    private final FormaPagamentoInputDisassembler disassembler;

    @GetMapping
    public List<FormaPagamentoModel> listar() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> buscarPorId(@PathVariable Long id) {
        var formaPagamento = service.buscarOuFalhar(id);
        return ResponseEntity.ok(assembler.toModel(formaPagamento));
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoModel> salvar(@RequestBody @Valid FormaPagamentoInput input) {

        var formaPagamento = disassembler.toEntity(input);
        service.salvar(formaPagamento);

        var model = assembler.toModel(formaPagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamento) {
        var formaPagamentoEncontrado = service.buscarOuFalhar(id);
        disassembler.copyToDomainObject(formaPagamento, formaPagamentoEncontrado);
        formaPagamentoEncontrado = service.salvar(formaPagamentoEncontrado);
        var formaPagamentoModel = assembler.toModel(formaPagamentoEncontrado);
        return ResponseEntity.ok(formaPagamentoModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
