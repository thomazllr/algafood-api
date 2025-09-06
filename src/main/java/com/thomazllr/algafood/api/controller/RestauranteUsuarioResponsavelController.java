package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.usuario.UsuarioModelAssembler;
import com.thomazllr.algafood.api.model.UsuarioModel;
import com.thomazllr.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    private final RestauranteService restauranteService;
    private final UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public List<UsuarioModel> listar(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarOuFalhar(restauranteId);
        var usuarios = restaurante.getUsuarios();
        return usuarioModelAssembler.toCollectionModel(usuarios);

    }
}
