package com.thomazllr.algafood.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.thomazllr.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final PedidoService service;

    private final EnvioEmailService envioEmailService;

    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = service.buscarOuFalhar(codigoPedido);
        pedido.confirmar();

        var mensagem = Mensagem
                .builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);

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
