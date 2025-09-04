package com.thomazllr.algafood.api.model.input.estado;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdInput {

    @NotNull
    private Long id;

}
