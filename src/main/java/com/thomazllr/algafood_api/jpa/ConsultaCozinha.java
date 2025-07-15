package com.thomazllr.algafood_api.jpa;

import com.thomazllr.algafood_api.Application;
import com.thomazllr.algafood_api.domain.Cozinha;
import com.thomazllr.algafood_api.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaCozinha {
    public static void main(String[] args) {

        ApplicationContext context = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cadastroCozinha = context.getBean(CozinhaRepository.class);

        var cozinhas = cadastroCozinha.listar();

        for (Cozinha cozinha : cozinhas) {
            System.out.printf("%s%n", cozinha);
        }

        var novaCozinha = Cozinha.builder().nome("Brasileira").build();

        novaCozinha = cadastroCozinha.salvar(novaCozinha);

        System.out.printf("%d - %s %n",  novaCozinha.getId(), novaCozinha.getNome());
        cozinhas = cadastroCozinha.listar();

        System.out.println("APÓS INSERIR UMA NOVA COZINHA");
        for (Cozinha cozinha : cozinhas) {
            System.out.printf("%s%n", cozinha);
        }

        var cozinhaEncontrada = cadastroCozinha.buscarPorId(1L);

        System.out.println("COZINHA ENCONTRADA");
        System.out.println(cozinhaEncontrada.getNome());

        cadastroCozinha.remover(cozinhaEncontrada);
        System.out.println("COZINHA REMOVIDA");
        cozinhas = cadastroCozinha.listar();
        for (Cozinha cozinha : cozinhas) {
            System.out.printf("%s%n", cozinha);
        }

    }
}
