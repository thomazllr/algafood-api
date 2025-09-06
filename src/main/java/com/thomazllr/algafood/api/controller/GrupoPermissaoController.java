package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.permissao.PermissaoModelAssembler;
import com.thomazllr.algafood.api.model.input.PermissaoModel;
import com.thomazllr.algafood.domain.service.GrupoService;
import com.thomazllr.algafood.domain.service.PermissaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
@RequiredArgsConstructor
public class GrupoPermissaoController {

    private final GrupoService grupoService;

    private final PermissaoModelAssembler permissaoModelAssembler;

    @GetMapping
    public List<PermissaoModel> listar(@PathVariable Long grupoId) {
        var grupo = grupoService.buscarOuFalhar(grupoId);
        var permissoes = grupo.getPermissoes();
        return permissaoModelAssembler.toCollectionModel(permissoes);
    }

    @PutMapping("{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
    }
}
