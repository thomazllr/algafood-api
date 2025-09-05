package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.cidade.CidadeInputDisassembler;
import com.thomazllr.algafood.api.assembler.cidade.CidadeModelAssembler;
import com.thomazllr.algafood.api.model.CidadeModel;
import com.thomazllr.algafood.api.model.input.cidade.CidadeInput;
import com.thomazllr.algafood.domain.entity.Cidade;
import com.thomazllr.algafood.domain.exception.EstadoNaoEncontradoException;
import com.thomazllr.algafood.domain.exception.NegocioException;
import com.thomazllr.algafood.domain.repository.CidadeRepository;
import com.thomazllr.algafood.domain.service.CidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeRepository repository;
    private final CidadeService service;

    private final CidadeModelAssembler assembler;
    private final CidadeInputDisassembler disassembler;

    @GetMapping
    public List<CidadeModel> listar() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeModel> buscarPorId(@PathVariable Long id) {
        var cidade = service.buscarOuFalhar(id);
        return ResponseEntity.ok(assembler.toModel(cidade));
    }

    @PostMapping
    public ResponseEntity<CidadeModel> salvar(@RequestBody @Valid CidadeInput input) {
        Cidade cidade = disassembler.toEntity(input);
        try {
            cidade = service.salvar(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(cidade));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CidadeModel> atualizar(@PathVariable Long id, @RequestBody CidadeInput cidade) {
        try {
            var cidadeEncontrada = service.buscarOuFalhar(id);
            disassembler.copyToDomainObject(cidade, cidadeEncontrada);
            cidadeEncontrada = service.salvar(cidadeEncontrada);
            return ResponseEntity.ok(assembler.toModel(cidadeEncontrada));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
