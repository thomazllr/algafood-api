package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.fotoproduto.FotoProdutoModelAssembler;
import com.thomazllr.algafood.api.model.FotoProdutoModel;
import com.thomazllr.algafood.api.model.input.fotoproduto.FotoProdutoInput;
import com.thomazllr.algafood.domain.entity.FotoProduto;
import com.thomazllr.algafood.domain.entity.Produto;
import com.thomazllr.algafood.domain.service.CatalagoFotoProdutoService;
import com.thomazllr.algafood.domain.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
@RequiredArgsConstructor
public class RestauranteProdutoFotoController {

    private final ProdutoService produtoService;
    private final CatalagoFotoProdutoService catalagoFotoProdutoService;
    private final FotoProdutoModelAssembler assembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput input) throws IOException {

        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        MultipartFile arquivo = input.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(input.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

        var fotoSalva = catalagoFotoProdutoService.salvar(fotoProduto, arquivo.getInputStream());

        return assembler.toModel(fotoSalva);

    }
}
