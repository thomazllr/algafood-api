package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.Usuario;
import com.thomazllr.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.thomazllr.algafood.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;


    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public void remover(Long id) {
        var usuario = buscarOuFalhar(id);
        repository.flush();
    }

    public Usuario buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }


}
