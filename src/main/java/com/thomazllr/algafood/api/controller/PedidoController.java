package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.api.assembler.pedido.PedidoInputDisassembler;
import com.thomazllr.algafood.api.assembler.pedido.PedidoModelAssembler;
import com.thomazllr.algafood.api.assembler.pedido.PedidoResumoModelAssembler;
import com.thomazllr.algafood.api.model.PedidoModel;
import com.thomazllr.algafood.api.model.PedidoResumoModel;
import com.thomazllr.algafood.api.model.input.pedido.PedidoInput;
import com.thomazllr.algafood.domain.entity.ItemPedido;
import com.thomazllr.algafood.domain.repository.PedidoRepository;
import com.thomazllr.algafood.domain.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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
    public List<PedidoResumoModel> pedidos() {
        var pedidos = repository.findAll();
        return pedidoResumoModelAssembler.toCollectionModel(pedidos);
    }

    @GetMapping("{id}")
    public PedidoModel pedidoPorId(@PathVariable Long id) {
        var pedido = service.buscarOuFalhar(id);
        return assembler.toModel(pedido);
    }

    @PostMapping
    public PedidoModel adicionar(@RequestBody @Valid PedidoInput input) {
        var pedido = disassembler.toEntity(input);
        pedido = service.salvar(pedido);
        return assembler.toModel(pedido);
    }


}
