package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.cozinha.CozinhaInputDisassembler;
import com.thomazllr.algafood.api.assembler.cozinha.CozinhaModelAssembler;
import com.thomazllr.algafood.api.model.CozinhaModel;
import com.thomazllr.algafood.api.model.input.cozinha.CozinhaInput;
import com.thomazllr.algafood.domain.Cozinha;
import com.thomazllr.algafood.domain.repository.CozinhaRepository;
import com.thomazllr.algafood.domain.service.CozinhaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaRepository repository;
    private final CozinhaService service;

    private final CozinhaModelAssembler assembler;
    private final CozinhaInputDisassembler disassembler;

    @GetMapping
    public List<CozinhaModel> listar() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CozinhaModel> buscarPorId(@PathVariable Long id) {

        var cozinha = service.buscarOuFalhar(id);
        var model = assembler.toModel(cozinha);

        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<CozinhaModel> salvar(@RequestBody @Valid CozinhaInput input) {

        var cozinha = disassembler.toEntity(input);

        cozinha = service.salvar(cozinha);

        var model = assembler.toModel(cozinha);

        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CozinhaModel> atualizar(@PathVariable Long id, @RequestBody CozinhaInput cozinha) {
        var cozinhaEncontrada = service.buscarOuFalhar(id);
        disassembler.copyToDomainObject(cozinha, cozinhaEncontrada);
        Cozinha cozinhaSalva = service.salvar(cozinhaEncontrada);
        return ResponseEntity.ok(assembler.toModel(cozinhaSalva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
