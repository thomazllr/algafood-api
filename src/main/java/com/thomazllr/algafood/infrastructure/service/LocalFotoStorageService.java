package com.thomazllr.algafood.infrastructure.service;

import com.thomazllr.algafood.domain.exception.StorageException;
import com.thomazllr.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${algafood.storage.local.diretorio-fotos}")
    private Path diretorioFotos;

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
    public InputStream recuperar(String nomeArquivo) {
        Path path = getArquivoPath(nomeArquivo);
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new StorageException("Não foi possível recuperar a foto", e);
        }
    }

    public Path getArquivoPath(String nomeArquivo) {
        return diretorioFotos.resolve(Path.of(nomeArquivo));
    }
}
