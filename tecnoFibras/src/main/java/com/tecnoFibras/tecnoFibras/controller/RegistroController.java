package com.tecnoFibras.tecnoFibras.controller;

import com.tecnoFibras.tecnoFibras.domain.Usuario;
import com.tecnoFibras.tecnoFibras.service.RegistroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "/registro/nuevo";
    }

    @PostMapping("/crear")
    public String crear(Usuario usuario, Model model) {
        try {
            registroService.registrar(usuario);
            model.addAttribute("exito", true);
            model.addAttribute("mensaje", "¡Registro exitoso! Revisa tu correo (" + usuario.getCorreo() + ") para activar tu cuenta.");
        } catch (Exception e) {
            model.addAttribute("exito", false);
            model.addAttribute("mensaje", "No se pudo completar el registro: " + e.getMessage());
        }
        return "/registro/confirmacion";
    }

    @GetMapping("/activar/{token}")
    public String activar(@PathVariable String token, Model model) {
        boolean activado = registroService.activar(token);
        model.addAttribute("exito", activado);
        model.addAttribute("mensaje", activado
                ? "¡Tu cuenta ha sido activada! Ya puedes iniciar sesión."
                : "El enlace de activación no es válido o ya fue usado.");
        return "/registro/confirmacion";
    }
}
