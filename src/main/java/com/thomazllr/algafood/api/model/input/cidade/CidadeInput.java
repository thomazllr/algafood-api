package com.thomazllr.algafood.api.model.input.cidade;

import com.thomazllr.algafood.api.model.input.estado.EstadoIdInput;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

    @NotBlank
    private String nome;

    private EstadoIdInput estado;
}
