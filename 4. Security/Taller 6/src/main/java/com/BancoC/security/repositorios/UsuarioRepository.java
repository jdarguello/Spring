package com.BancoC.security.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BancoC.security.modelos.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsername(String username);

}
