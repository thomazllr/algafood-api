package com.thomazllr.algafood.api.controller.openapi;

import com.thomazllr.algafood.api.model.CidadeModel;
import com.thomazllr.algafood.api.model.input.cidade.CidadeInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Cidades")
public interface CidadeControllerOpenApi {

    @Operation(summary = "Lista as cidades")
    List<CidadeModel> listar();

    @Operation(summary = "Busca uma cidade por Id",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID da cidade inválido",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Cidade não encontrada",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    CidadeModel buscar(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long cidadeId);


    @Operation(summary = "Cadastra uma cidade", description = "Cadastro de uma cidade, " +
                                                              "necessita de um estado e um nome válido")
    CidadeModel adicionar(@RequestBody(description = "Representação de uma nova cidade", required = true) CidadeInput cidadeInput);

    @Operation(summary = "Atualiza uma cidade", description = "Atualização de uma cidade, " +
                                                              "necessita de um estado e um nome válido")
    ResponseEntity<CidadeModel> atualizar(@PathVariable Long id, @RequestBody CidadeInput cidade);
}
