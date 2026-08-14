package com.tecnoFibras.tecnoFibras.controller;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private final MessageSource messageSource;

    public HomeController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/acceso_denegado")
    @PostMapping("/acceso_denegado")
    public String accesoDenegado() {
        return "denegado";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @PostMapping("/contacto")
    public String contactoEnviar(RedirectAttributes redirectAttributes, Locale locale) {
        redirectAttributes.addFlashAttribute("todoOk", messageSource.getMessage("contacto.enviado", null, locale));
        return "redirect:/";
    }
}
