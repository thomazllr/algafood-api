package com.thomazllr.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto foto);

    void remover(String nomeArquivo);

    FotoRecuperada recuperar(String nomeArquivo);

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
        private String contentType;
        private InputStream inputStream;
    }

    @Getter
    @Builder
    class FotoRecuperada {
        private String url;
        private InputStream inputStream;

        public boolean temUrl() {
            return url != null;
        }

        public boolean temInputStream() {
            return inputStream != null;
        }
    }
}
