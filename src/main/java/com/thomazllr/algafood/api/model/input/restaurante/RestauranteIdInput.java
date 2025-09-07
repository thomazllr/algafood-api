package com.thomazllr.algafood.api.model.input.restaurante;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteIdInput {
    @NotNull
    private Long id;
}
