package com.tecnoFibras.tecnoFibras.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/acceso_denegado")
    public String accesoDenegado() {
        return "denegado";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @PostMapping("/contacto")
    public String contactoEnviar(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("todoOk", "¡Mensaje enviado correctamente!");
        return "redirect:/";
    }
}
