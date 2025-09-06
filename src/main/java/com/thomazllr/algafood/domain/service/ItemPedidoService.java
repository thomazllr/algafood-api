package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.ItemPedido;
import com.thomazllr.algafood.domain.repository.ItemPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository repository;

    public ItemPedido salvar(ItemPedido itemPedido) {
        return repository.save(itemPedido);
    }
}
