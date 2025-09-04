package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.RestauranteModelAssembler;
import com.thomazllr.algafood.api.disassembler.RestauranteInputDisassembler;
import com.thomazllr.algafood.api.model.RestauranteModel;
import com.thomazllr.algafood.api.model.input.RestauranteInput;
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
    private final RestauranteModelAssembler assembler;
    private final RestauranteInputDisassembler disassembler;

    @GetMapping
    public List<RestauranteModel> listar() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteModel> buscarPorId(@PathVariable Long id) {
        var restaurante = service.buscarOuFalhar(id);

        var model = assembler.toModel(restaurante);

        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<RestauranteModel> salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {

            var restaurante = disassembler.toEntity(restauranteInput);

            service.salvar(restaurante);

            var model = assembler.toModel(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED).body(model);
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
