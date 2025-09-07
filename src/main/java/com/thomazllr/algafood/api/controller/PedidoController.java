package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.pedido.PedidoInputDisassembler;
import com.thomazllr.algafood.api.assembler.pedido.PedidoModelAssembler;
import com.thomazllr.algafood.api.assembler.pedido.PedidoResumoModelAssembler;
import com.thomazllr.algafood.api.model.PedidoModel;
import com.thomazllr.algafood.api.model.PedidoResumoModel;
import com.thomazllr.algafood.api.model.input.pedido.PedidoInput;
import com.thomazllr.algafood.domain.repository.PedidoRepository;
import com.thomazllr.algafood.domain.filter.PedidoFilter;
import com.thomazllr.algafood.domain.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.thomazllr.algafood.infrastructure.repository.spec.PedidoSpecs.usandoFiltro;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PedidoRepository repository;
    private final PedidoModelAssembler assembler;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;

    private final PedidoInputDisassembler disassembler;

    @GetMapping
    public Page<PedidoResumoModel> pedidos(PedidoFilter filtro, Pageable pageable) {
        var pedidos = repository.findAll(usandoFiltro(filtro), pageable);

        List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos.getContent());

        return new PageImpl<>(pedidosModel, pageable, pedidos.getTotalElements());
    }


    @GetMapping("/{codigoPedido}")
    public PedidoModel pedidoPorId(@PathVariable String codigoPedido) {
        var pedido = service.buscarOuFalhar(codigoPedido);
        return assembler.toModel(pedido);
    }

    @PostMapping
    public PedidoModel adicionar(@RequestBody @Valid PedidoInput input) {
        var pedido = disassembler.toEntity(input);
        pedido = service.salvar(pedido);
        return assembler.toModel(pedido);
    }


}
