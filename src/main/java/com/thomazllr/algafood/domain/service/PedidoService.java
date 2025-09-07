package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.Pedido;
import com.thomazllr.algafood.domain.entity.Produto;
import com.thomazllr.algafood.domain.exception.NegocioException;
import com.thomazllr.algafood.domain.exception.PedidoNaoEncontradoException;
import com.thomazllr.algafood.domain.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ProdutoService produtoService;
    private final RestauranteService restauranteService;
    private final CidadeService cidadeService;
    private final UsuarioService usuarioService;
    private final FormaPagamentoService formaPagamentoService;

    public Pedido buscarOuFalhar(Long id) {
        return repository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {

        validarPedido(pedido);
        validarItens(pedido);

        pedido.calcularValorTotal();

        return repository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        var restauranteId = pedido.getRestaurante().getId();
        var cidadeId = pedido.getEnderecoEntrega().getCidade().getId();
        var clienteId = pedido.getCliente().getId();
        var formaPagamentoId = pedido.getFormaPagamento().getId();

        var restaurante = restauranteService.buscarOuFalhar(restauranteId);
        var cidade = cidadeService.buscarOuFalhar(cidadeId);
        var cliente = usuarioService.buscarOuFalhar(clienteId);
        var formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setTaxaFrete(restaurante.getTaxaFrete());

        if (restaurante.naoAceitaFormaDePagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }

    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = produtoService.buscarOuFalhar(
                    pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
            item.calcularPrecoTotal();
        });
    }

}
