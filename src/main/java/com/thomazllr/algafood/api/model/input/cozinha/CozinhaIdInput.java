package com.thomazllr.algafood.api.model.input.cozinha;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaIdInput {

    @NotNull
    private Long id;
}
