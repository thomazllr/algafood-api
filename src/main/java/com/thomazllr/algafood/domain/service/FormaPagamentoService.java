package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.FormaPagamento;
import com.thomazllr.algafood.domain.exception.EntidadeEmUsoException;
import com.thomazllr.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.thomazllr.algafood.domain.repository.FormaPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository repository;

    public static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de Pagamento de ID: %d está em uso";

    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return repository.save(formaPagamento);
    }

    public void remover(Long id) {
        var formaPagamento = buscarOuFalhar(id);
        try {
            repository.delete(formaPagamento);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, id)
            );
        }
    }

    public FormaPagamento buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }


}
