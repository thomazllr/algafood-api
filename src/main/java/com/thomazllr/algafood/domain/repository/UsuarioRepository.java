package com.thomazllr.algafood.domain.repository;

import com.thomazllr.algafood.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
