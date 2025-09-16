package com.thomazllr.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    @Getter
    @Setter
    @Builder
    class Mensagem {
        @Singular
        private Set<String> destinatarios;
        private String assunto;
        private String corpo;

        @Singular("variavel")
        private Map<String, Object> variaveis;
    }
}
