package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.Restaurante;
import com.thomazllr.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.thomazllr.algafood.domain.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestauranteService {

    private final RestauranteRepository repository;
    private final CozinhaService cozinhaService;
    private final CidadeService cidadeService;
    private final FormaPagamentoService formaPagamentoService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        var cozinhaId = restaurante.getCozinha().getId();
        var cidadeId = restaurante.getEndereco().getCidade().getId();

        var cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
        var cidade = cidadeService.buscarOuFalhar(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        return repository.save(restaurante);
    }

    public void remover(Long id) {
        var restaurante = buscarOuFalhar(id);
        repository.delete(restaurante);
    }

    public Restaurante buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);
        restaurante.inativar();
    }

    @Transactional
    public void abrir(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);
        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);
        restaurante.fechar();
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        var restaurante = this.buscarOuFalhar(restauranteId);
        var formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
        restaurante.disassociarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        var restaurante = this.buscarOuFalhar(restauranteId);
        var formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
        restaurante.associarFormaPagamento(formaPagamento);
    }


}
