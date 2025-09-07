package com.thomazllr.algafood.api.model.input.cliente;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteIdInput {
    @NotNull
    private Long id;
}
