package com.thomazllr.algafood.api.model.input.cidade;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeIdInput {

    @NotNull
    private Long id;
}
