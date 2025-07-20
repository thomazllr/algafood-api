package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.domain.Estado;
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

    @GetMapping
    public List<Estado> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscarPorId(@PathVariable Long id) {
        var estado = service.buscarOuFalhar(id);
        return ResponseEntity.ok(estado);
    }

    @PostMapping
    public ResponseEntity<Estado> salvar(@RequestBody Estado estado) {
        service.salvar(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
        var estadoEncontrado = service.buscarOuFalhar(id);
        estadoEncontrado.setNome(estado.getNome());
        return ResponseEntity.ok(service.salvar(estadoEncontrado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

}
