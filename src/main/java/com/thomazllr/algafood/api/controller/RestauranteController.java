package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.domain.Restaurante;
import com.thomazllr.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.thomazllr.algafood.domain.exception.NegocioException;
import com.thomazllr.algafood.domain.repository.RestauranteRepository;
import com.thomazllr.algafood.domain.service.RestauranteService;
import jakarta.validation.Valid;
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
    private final RestauranteService service;

    @GetMapping
    public List<Restaurante> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {
        var restaurante = service.buscarOuFalhar(id);
        return ResponseEntity.ok(restaurante);
    }

    @PostMapping
    public ResponseEntity<Restaurante> salvar(@RequestBody @Valid Restaurante restaurante) {
        try {
            service.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizar(@PathVariable Long id, @RequestBody @Valid Restaurante restaurante) {
        try {
            var restauranteEncontrado = service.buscarOuFalhar(id);

            restauranteEncontrado.setNome(restaurante.getNome());
            restauranteEncontrado.setTaxaFrete(restaurante.getTaxaFrete());
            restauranteEncontrado.setCozinha(restaurante.getCozinha());

            restauranteEncontrado = service.salvar(restauranteEncontrado);
            return ResponseEntity.ok(restauranteEncontrado);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
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
