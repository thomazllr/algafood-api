package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.usuario.UsuarioInputDisassembler;
import com.thomazllr.algafood.api.assembler.usuario.UsuarioModelAssembler;
import com.thomazllr.algafood.api.model.UsuarioModel;
import com.thomazllr.algafood.api.model.input.usuario.UsuarioInput;
import com.thomazllr.algafood.domain.repository.UsuarioRepository;
import com.thomazllr.algafood.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository repository;
    private final UsuarioService service;

    private final UsuarioModelAssembler assembler;
    private final UsuarioInputDisassembler disassembler;

    @GetMapping
    public List<UsuarioModel> listar() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> buscarPorId(@PathVariable Long id) {
        var usuario = service.buscarOuFalhar(id);
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @PostMapping
    public ResponseEntity<UsuarioModel> salvar(@RequestBody @Valid UsuarioInput input) {

        var usuario = disassembler.toEntity(input);
        service.salvar(usuario);

        var model = assembler.toModel(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInput usuario) {
        var usuarioEncontrado = service.buscarOuFalhar(id);
        disassembler.copyToDomainObject(usuario, usuarioEncontrado);
        usuarioEncontrado = service.salvar(usuarioEncontrado);
        var usuarioModel = assembler.toModel(usuarioEncontrado);
        return ResponseEntity.ok(usuarioModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
    
}
