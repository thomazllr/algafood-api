package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.grupo.GrupoInputDisassembler;
import com.thomazllr.algafood.api.assembler.grupo.GrupoModelAssembler;
import com.thomazllr.algafood.api.model.GrupoModel;
import com.thomazllr.algafood.api.model.input.grupo.GrupoInput;
import com.thomazllr.algafood.domain.repository.GrupoRepository;
import com.thomazllr.algafood.domain.service.GrupoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final GrupoRepository repository;
    private final GrupoService service;

    private final GrupoModelAssembler assembler;
    private final GrupoInputDisassembler disassembler;

    @GetMapping
    public List<GrupoModel> listar() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoModel> buscarPorId(@PathVariable Long id) {
        var grupo = service.buscarOuFalhar(id);
        return ResponseEntity.ok(assembler.toModel(grupo));
    }

    @PostMapping
    public ResponseEntity<GrupoModel> salvar(@RequestBody @Valid GrupoInput input) {

        var grupo = disassembler.toEntity(input);
        service.salvar(grupo);

        var model = assembler.toModel(grupo);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoModel> atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupo) {
        var grupoEncontrado = service.buscarOuFalhar(id);
        disassembler.copyToDomainObject(grupo, grupoEncontrado);
        grupoEncontrado = service.salvar(grupoEncontrado);
        var grupoModel = assembler.toModel(grupoEncontrado);
        return ResponseEntity.ok(grupoModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
