package com.thomazllr.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ERRO_DE_NEGOCIO("/erro-de-negocio", "Erro de negócio interno"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos."),
    ERROR_DE_SISTEMA("/error-de-sistema", "Erro de sistema");

    private String uri;
    private String title;

    ErrorType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
