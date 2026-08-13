package com.tecnoFibras.tecnoFibras.service;

import com.tecnoFibras.tecnoFibras.domain.Rol;
import com.tecnoFibras.tecnoFibras.domain.Usuario;
import com.tecnoFibras.tecnoFibras.repository.RolRepository;
import com.tecnoFibras.tecnoFibras.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> getUsuario(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> getUsuarioByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Rol> getRolDeUsuario(Integer idUsuario) {
        return rolRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Transactional
    public void save(Usuario usuario, String nombreRol) {
        boolean esNuevo = usuario.getIdUsuario() == null;

        if (esNuevo) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                Usuario existente = usuarioRepository.findById(usuario.getIdUsuario())
                        .orElseThrow(() -> new IllegalArgumentException("Usuario a modificar no encontrado."));
                usuario.setPassword(existente.getPassword());
            } else {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
        }

        usuario = usuarioRepository.save(usuario);

        if (nombreRol != null && !nombreRol.isBlank()) {
            Rol rol = rolRepository.findByUsuario_IdUsuario(usuario.getIdUsuario())
                    .orElse(new Rol());
            rol.setUsuario(usuario);
            rol.setNombre(nombreRol);
            rolRepository.save(rol);
        }
    }

    @Transactional
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
