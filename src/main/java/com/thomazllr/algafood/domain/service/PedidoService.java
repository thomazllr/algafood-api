package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.Pedido;
import com.thomazllr.algafood.domain.exception.PedidoNaoEncontradoException;
import com.thomazllr.algafood.domain.repository.ItemPedidoRepository;
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

    public Pedido buscarOuFalhar(Long id) {
        return repository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        var restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        pedido.setRestaurante(restaurante);
        pedido.setTaxaFrete(restaurante.getTaxaFrete());

        for (var item : pedido.getItens()) {
            var produto = produtoService.buscarOuFalhar(restaurante.getId(), item.getProduto().getId());
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
            item.setPedido(pedido);
            item.calcularPrecoTotal();
        }

        pedido.calcularValorTotal();

        return repository.save(pedido);
    }

}
