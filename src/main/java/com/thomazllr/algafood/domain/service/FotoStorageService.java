package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.FotoProduto;
import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto foto);

    void remover(String nomeArquivo);

    InputStream recuperar(String nomeArquivo);

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        armazenar(novaFoto);
        if (nomeArquivoAntigo != null) {
            remover(nomeArquivoAntigo);
        }
    }

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID() + "_" + nomeOriginal;
    }

    @Getter
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }
}
