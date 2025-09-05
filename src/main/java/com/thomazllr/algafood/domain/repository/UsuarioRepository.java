package com.thomazllr.algafood.domain.repository;

import com.thomazllr.algafood.domain.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

}
