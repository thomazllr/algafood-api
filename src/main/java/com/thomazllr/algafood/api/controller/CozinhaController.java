package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.domain.Cozinha;
import com.thomazllr.algafood.domain.repository.CozinhaRepository;
import com.thomazllr.algafood.domain.service.CozinhaService;
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

    @GetMapping
    public List<Cozinha> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarOuFalhar(id));
    }

    @PostMapping
    public ResponseEntity<Cozinha> salvar(@RequestBody Cozinha cozinha) {
        service.salvar(cozinha);
        return ResponseEntity.status(HttpStatus.CREATED).body(cozinha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        var cozinhaEncontrada = service.buscarOuFalhar(id);
        cozinhaEncontrada.setNome(cozinha.getNome());
        Cozinha cozinhaSalva = service.salvar(cozinhaEncontrada);
        return ResponseEntity.ok(cozinhaSalva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
