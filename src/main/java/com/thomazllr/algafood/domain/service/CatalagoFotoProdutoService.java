package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.FotoProduto;
import com.thomazllr.algafood.domain.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Optional;

import static com.thomazllr.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
@RequiredArgsConstructor
public class CatalagoFotoProdutoService {

    private final ProdutoRepository repository;

    private final FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream dadosArquivo) {
        var restauranteId = fotoProduto.getRestauranteId();
        var produtoId = fotoProduto.getProduto().getId();

        Optional<FotoProduto> fotoExistente = repository.findFotoProdutoById(restauranteId, produtoId);

        if (fotoExistente.isPresent()) {
            repository.delete(fotoExistente.get());
        }

        var foto = repository.save(fotoProduto);
        repository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        fotoStorageService.armazenar(novaFoto);

        return foto;
    }
}
