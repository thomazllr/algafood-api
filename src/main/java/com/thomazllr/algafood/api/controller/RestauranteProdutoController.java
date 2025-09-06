package com.thomazllr.algafood.api.controller;


import com.thomazllr.algafood.api.assembler.produto.ProdutoInputDisassembler;
import com.thomazllr.algafood.api.assembler.produto.ProdutoModelAssembler;
import com.thomazllr.algafood.api.model.ProdutoModel;
import com.thomazllr.algafood.api.model.input.produto.ProdutoInput;
import com.thomazllr.algafood.domain.service.ProdutoService;
import com.thomazllr.algafood.domain.service.RestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes/{id}/produtos")
public class RestauranteProdutoController {

    private final RestauranteService restauranteService;

    private final ProdutoService produtoService;

    private final ProdutoModelAssembler produtoModelAssembler;

    private final ProdutoInputDisassembler produtoInputDisassembler;

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long id) {
        var restaurante = restauranteService.buscarOuFalhar(id);
        var produtos = restaurante.getProdutos();
        return produtoModelAssembler.toCollectionModel(produtos);
    }

    @GetMapping("{produtoId}")
    public ProdutoModel buscarPorId(@PathVariable Long id, @PathVariable Long produtoId) {
        var produto = produtoService.buscarOuFalhar(id, produtoId);
        return produtoModelAssembler.toModel(produto);
    }

    @PostMapping
    public ResponseEntity<ProdutoModel> adicionar(@PathVariable Long id, @RequestBody @Valid ProdutoInput input) {
        var restaurante = restauranteService.buscarOuFalhar(id);
        var produto = produtoInputDisassembler.toEntity(input);
        produto.setRestaurante(restaurante);
        produto = produtoService.salvar(produto);
        var model = produtoModelAssembler.toModel(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("{produtoId}")
    public ResponseEntity<ProdutoModel> atualizar(@PathVariable Long id,
                                                  @PathVariable Long produtoId,
                                                  @RequestBody @Valid ProdutoInput produtoInput) {
        var produto = produtoService.buscarOuFalhar(id, produtoId);
        produtoInputDisassembler.copyToDomainObject(produtoInput, produto);
        produto = produtoService.salvar(produto);
        return ResponseEntity.ok(produtoModelAssembler.toModel(produto));
    }

}
