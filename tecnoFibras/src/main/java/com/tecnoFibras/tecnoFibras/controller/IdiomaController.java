package com.tecnoFibras.tecnoFibras.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

@Controller
public class IdiomaController {

    private final LocaleResolver localeResolver;

    public IdiomaController(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @GetMapping("/idioma")
    public String cambiarIdioma(@RequestParam String lang, HttpServletRequest request, HttpServletResponse response) {
        localeResolver.setLocale(request, response, new Locale(lang));
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }
}
