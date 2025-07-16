package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.domain.Estado;
import com.thomazllr.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController {

    private final EstadoRepository repository;

    @GetMapping
    public List<Estado> listar() {
        return repository.listar();
    }
}
