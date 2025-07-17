package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.domain.Restaurante;
import com.thomazllr.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.repository.RestauranteRepository;
import com.thomazllr.algafood.domain.service.CadastroRestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteRepository repository;
    private final CadastroRestauranteService service;

    @GetMapping
    public List<Restaurante> listar() {
        System.out.println("Passou por aqui!!!");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {
        var restaurante = repository.findById(id).orElse(null);
        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = service.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {

        var restauranteEncontrado = repository.findById(id).orElse(null);

        if (restauranteEncontrado != null) {
            try {
                restauranteEncontrado.setNome(restaurante.getNome());
                restauranteEncontrado.setTaxaFrete(restaurante.getTaxaFrete());
                restauranteEncontrado.setCozinha(restaurante.getCozinha());
                restauranteEncontrado = service.salvar(restauranteEncontrado);
                return ResponseEntity.ok(restauranteEncontrado);
            } catch (EntidadeNaoEncontradaException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            service.remover(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/com-frete-gratis")
    public List<Restaurante> filtro(String nome) {
        return repository.findComFreteGratis(nome);
    }

    @GetMapping("/primeiro")
    public Optional<Restaurante> primeiro() {
        return repository.buscarPrimeiro();
    }



}
