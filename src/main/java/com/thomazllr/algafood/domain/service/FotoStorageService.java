package com.thomazllr.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

public interface FotoStorageService {

    void armazenar(NovaFoto foto);

    @Getter
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }
}
