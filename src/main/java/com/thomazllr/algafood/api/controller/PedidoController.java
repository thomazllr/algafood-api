package com.thomazllr.algafood.api.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.thomazllr.algafood.api.assembler.pedido.PedidoInputDisassembler;
import com.thomazllr.algafood.api.assembler.pedido.PedidoModelAssembler;
import com.thomazllr.algafood.api.assembler.pedido.PedidoResumoModelAssembler;
import com.thomazllr.algafood.api.model.PedidoModel;
import com.thomazllr.algafood.api.model.input.pedido.PedidoInput;
import com.thomazllr.algafood.domain.repository.PedidoRepository;
import com.thomazllr.algafood.domain.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter.filterOutAllExcept;
import static com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter.serializeAll;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

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
    public MappingJacksonValue pedidos(@RequestParam(required = false) String campos) {
        var pedidos = repository.findAll();
        var pedidosResumoModels = pedidoResumoModelAssembler.toCollectionModel(pedidos);

        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosResumoModels);

        boolean temCampos = isNotBlank(campos);

        var camposLimpos = temCampos ? limparCampos(campos) : new String[0];

        var filtro = temCampos ? filterOutAllExcept(camposLimpos) : serializeAll();

        var filterProvider = new SimpleFilterProvider().addFilter("pedidoFilter", filtro);

        pedidosWrapper.setFilters(filterProvider);
        return pedidosWrapper;
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


    private String[] limparCampos(String campos) {
        return Arrays.stream(campos.split(","))
                .map(String::trim)
                .toArray(String[]::new);
    }


}
