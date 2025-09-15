package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.fotoproduto.FotoProdutoModelAssembler;
import com.thomazllr.algafood.api.model.FotoProdutoModel;
import com.thomazllr.algafood.api.model.input.fotoproduto.FotoProdutoInput;
import com.thomazllr.algafood.domain.entity.FotoProduto;
import com.thomazllr.algafood.domain.entity.Produto;
import com.thomazllr.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.thomazllr.algafood.domain.service.CatalagoFotoProdutoService;
import com.thomazllr.algafood.domain.service.FotoStorageService;
import com.thomazllr.algafood.domain.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
@RequiredArgsConstructor
public class RestauranteProdutoFotoController {

    private final ProdutoService produtoService;
    private final CatalagoFotoProdutoService catalagoFotoProdutoService;
    private final FotoProdutoModelAssembler assembler;
    private final FotoStorageService fotoStorageService;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoModel buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var foto = catalagoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return assembler.toModel(foto);
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId,
                                        @PathVariable Long produtoId,
                                        @RequestHeader("Accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        try {
            var foto = catalagoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

            MediaType contentType = MediaType.parseMediaType(foto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidade(contentType, mediaTypesAceitas);

            var fotoRecuperada = fotoStorageService.recuperar(foto.getNomeArquivo());

            if (fotoRecuperada.temUrl()) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();

            } else {
                return ResponseEntity.ok().
                        contentType(MediaType.valueOf(foto.getContentType()))
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }


        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping
    public void deletar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var foto = catalagoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        catalagoFotoProdutoService.remover(foto);
    }

    private void verificarCompatibilidade(MediaType contentType, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaType -> mediaType.isCompatibleWith(contentType));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException("Mídia não suportada");
        }

    }
}
