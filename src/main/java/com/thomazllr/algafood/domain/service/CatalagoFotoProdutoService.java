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
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        var restauranteId = foto.getRestauranteId();
        var produtoId = foto.getProduto().getId();
        String nomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoExistente = repository.findFotoProdutoById(restauranteId, produtoId);

        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            repository.delete(fotoExistente.get());
        }

        foto.setNomeArquivo(nomeArquivo);

        foto = repository.save(foto);
        repository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }
}
