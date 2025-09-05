package com.thomazllr.algafood.api.model.input.senha;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaInput {
    @NotBlank
    private String senha;
    @NotBlank
    private String senhaNova;
}
