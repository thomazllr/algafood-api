package com.thomazllr.algafood.api.model.input.grupo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoInput {

    @NotBlank
    private String nome;
}
