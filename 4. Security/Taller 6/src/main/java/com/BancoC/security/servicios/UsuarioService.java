package com.BancoC.security.servicios;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BancoC.security.modelos.Usuario;
import com.BancoC.security.repositorios.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    private UsuarioRepository repository;

    public UsuarioService (UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario crearUsuario(Usuario usuario) {
        return this.repository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return this.repository.findByUsername(username).get();
    }
}
