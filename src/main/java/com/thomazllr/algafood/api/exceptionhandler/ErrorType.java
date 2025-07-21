package com.thomazllr.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    ERRO_DE_NEGOCIO("/erro-de-negocio", "Erro de negócio interno"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido");

    private String uri;
    private String title;

    ErrorType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
