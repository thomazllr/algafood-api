package com.thomazllr.algafood.domain.service;

import com.thomazllr.algafood.domain.entity.Usuario;
import com.thomazllr.algafood.domain.exception.NegocioException;
import com.thomazllr.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.thomazllr.algafood.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    private final GrupoService grupoService;

    @Transactional
    public Usuario salvar(Usuario usuario) {

        repository.detach(usuario);

        Optional<Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException("Usuário com email %s já cadastrado".formatted(usuario.getEmail()));
        }


        return repository.save(usuario);
    }

    public void remover(Long id) {
        var usuario = buscarOuFalhar(id);
        repository.delete(usuario);
    }

    public Usuario buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public void atualizarSenha(Long id, String senhaAtual, String novaSenha) {
        var usuario = buscarOuFalhar(id);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada incorretamente.");
        }

        usuario.setSenha(novaSenha);
        this.salvar(usuario);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        var usuario = this.buscarOuFalhar(usuarioId);
        var grupo = grupoService.buscarOuFalhar(grupoId);
        usuario.adicionarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        var usuario = this.buscarOuFalhar(usuarioId);
        var grupo = grupoService.buscarOuFalhar(grupoId);
        usuario.removerGrupo(grupo);
    }


}
