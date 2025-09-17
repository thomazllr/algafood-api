package com.thomazllr.algafood.domain.listener;

import com.thomazllr.algafood.domain.event.PedidoCanceladoEvent;
import com.thomazllr.algafood.domain.service.EnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.thomazllr.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
@RequiredArgsConstructor
public class NotificacaoClientePedidoCanceladoListener {

    private final EnvioEmailService envioEmailService;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {

        var pedido = event.getPedido();

        var mensagem = Mensagem
                .builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
                .corpo("pedido-cancelado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);

    }
}
