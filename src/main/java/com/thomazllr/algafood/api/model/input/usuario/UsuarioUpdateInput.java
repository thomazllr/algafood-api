package com.thomazllr.algafood.api.model.input.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateInput {
    @NotBlank
    private String nome;

    @NotBlank
    private String email;
}
