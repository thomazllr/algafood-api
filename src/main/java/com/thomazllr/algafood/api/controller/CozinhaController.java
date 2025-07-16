package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.domain.Cozinha;
import com.thomazllr.algafood.domain.repository.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaRepository repository;

    @GetMapping
    public List<Cozinha> listar() {
        return repository.listar();
    }

    @GetMapping("/{id}")
    public Cozinha buscarPorId(@PathVariable Long id) {
        return repository.buscarPorId(id);
    }
}
