package com.thomazllr.algafood.api.model.input.cidade;

import com.thomazllr.algafood.api.model.input.estado.EstadoIdInput;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

    @Schema(example = "Uberlândia")
    @NotBlank
    private String nome;

    private EstadoIdInput estado;
}
