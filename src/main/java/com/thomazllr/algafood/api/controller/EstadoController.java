package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.estado.EstadoInputDisassembler;
import com.thomazllr.algafood.api.assembler.estado.EstadoModelAssembler;
import com.thomazllr.algafood.api.model.EstadoModel;
import com.thomazllr.algafood.api.model.input.estado.EstadoInput;
import com.thomazllr.algafood.domain.repository.EstadoRepository;
import com.thomazllr.algafood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController {

    private final EstadoRepository repository;
    private final EstadoService service;

    private final EstadoModelAssembler assembler;
    private final EstadoInputDisassembler disassembler;

    @GetMapping
    public List<EstadoModel> listar() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoModel> buscarPorId(@PathVariable Long id) {
        var estado = service.buscarOuFalhar(id);
        return ResponseEntity.ok(assembler.toModel(estado));
    }

    @PostMapping
    public ResponseEntity<EstadoModel> salvar(@RequestBody EstadoInput input) {

        var estado = disassembler.toEntity(input);
        service.salvar(estado);

        var model = assembler.toModel(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoModel> atualizar(@PathVariable Long id, @RequestBody EstadoInput estado) {
        var estadoEncontrado = service.buscarOuFalhar(id);
        disassembler.copyToDomainObject(estado, estadoEncontrado);
        estadoEncontrado = service.salvar(estadoEncontrado);
        var estadoModel = assembler.toModel(estadoEncontrado);
        return ResponseEntity.ok(estadoModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
