package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final PedidoService service;
    private final PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = service.buscarOuFalhar(codigoPedido);
        pedido.confirmar();
        pedidoRepository.save(pedido);

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
        pedidoRepository.save(pedido);
    }

}
