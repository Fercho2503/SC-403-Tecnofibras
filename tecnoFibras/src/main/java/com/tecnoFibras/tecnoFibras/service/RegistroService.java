package com.tecnoFibras.tecnoFibras.service;

import com.tecnoFibras.tecnoFibras.domain.Usuario;
import com.tecnoFibras.tecnoFibras.repository.UsuarioRepository;
import jakarta.mail.MessagingException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroService {

    private final CorreoService correoService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @Value("${servidor.http}")
    private String servidorHttp;

    public RegistroService(CorreoService correoService, UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.correoService = correoService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    // Registro público: SIEMPRE entra como CLIENTE, nunca puede elegir su rol
    @Transactional
    public void registrar(Usuario usuario) throws MessagingException {
        String token = UUID.randomUUID().toString();
        usuario.setTokenActivacion(token);
        usuario.setActivo(false);

        usuarioService.save(usuario, "CLIENTE");

        String enlace = "http://" + servidorHttp + "/registro/activar/" + token;
        String contenido = "<h2>Bienvenido a TecnoFibras</h2>"
                + "<p>Gracias por registrarte. Haz clic en el siguiente enlace para activar tu cuenta:</p>"
                + "<p><a href='" + enlace + "'>Activar mi cuenta</a></p>"
                + "<p>Si no solicitaste esta cuenta, puedes ignorar este correo.</p>";

        correoService.enviarCorreoHtml(usuario.getCorreo(), "Activa tu cuenta en TecnoFibras", contenido);
    }

    @Transactional
    public boolean activar(String token) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByTokenActivacion(token);
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setActivo(true);
        usuario.setTokenActivacion(null);
        usuarioRepository.save(usuario);
        return true;
    }
}
