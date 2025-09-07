package com.thomazllr.algafood.api.controller;

import com.thomazllr.algafood.domain.service.FluxoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos/{codigoPedido}")
public class FluxoPedidoController {

    private final FluxoPedidoService service;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigoPedido) {
        service.confirmar(codigoPedido);
    }

    @PutMapping("/entregar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigoPedido) {
        service.entregar(codigoPedido);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigoPedido) {
        service.cancelar(codigoPedido);
    }
}
