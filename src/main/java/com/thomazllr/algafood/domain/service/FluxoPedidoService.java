package com.thomazllr.algafood.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final PedidoService service;

    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = service.buscarOuFalhar(codigoPedido);
        pedido.confirmar();

    }

    @Transactional
    public void entregar(String codigoPedido) {
        var pedido = service.buscarOuFalhar(codigoPedido);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        var pedido = service.buscarOuFalhar(codigoPedido);
        pedido.cancelar();
    }

}
