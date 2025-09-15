package com.thomazllr.algafood.infrastructure.service;

import com.thomazllr.algafood.core.storage.StorageProperties;
import com.thomazllr.algafood.domain.exception.StorageException;
import com.thomazllr.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties properties;

    @Override
    public void armazenar(NovaFoto foto) {

        Path arquivoPath = getArquivoPath(foto.getNomeArquivo());

        try {
            FileCopyUtils.copy(foto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (IOException e) {
            throw new StorageException("Não foi possível armazenar a foto", e);
        }

    }

    @Override
    public void remover(String nomeArquivo) {
        Path arquivoPath = getArquivoPath(nomeArquivo);
        try {
            Files.deleteIfExists(arquivoPath);
        } catch (IOException e) {
            throw new StorageException("Não foi possível deletar a foto", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path path = getArquivoPath(nomeArquivo);
            InputStream inputStream = Files.newInputStream(path);
            return FotoRecuperada
                    .builder()
                    .inputStream(inputStream)
                    .build();
        } catch (IOException e) {
            throw new StorageException("Não foi possível recuperar a foto", e);
        }
    }

    public Path getArquivoPath(String nomeArquivo) {
        return properties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }
}
