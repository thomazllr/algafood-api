package com.thomazllr.algafood.api.model.input.estado;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstadoInput {

    @NotBlank
    private String nome;
}
