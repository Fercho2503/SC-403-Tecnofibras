package com.tecnoFibras.tecnoFibras.service;

import com.tecnoFibras.tecnoFibras.domain.Rol;
import com.tecnoFibras.tecnoFibras.domain.Usuario;
import com.tecnoFibras.tecnoFibras.repository.RolRepository;
import com.tecnoFibras.tecnoFibras.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        if (usuario.getActivo() != null && !usuario.getActivo()) {
            throw new UsernameNotFoundException("El usuario está inactivo: " + username);
        }

        Optional<Rol> rolOpt = rolRepository.findByUsuario_IdUsuario(usuario.getIdUsuario());
        String nombreRol = rolOpt.map(Rol::getNombre).orElse("CLIENTE");

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + nombreRol.toUpperCase())))
                .build();
    }
}
