package com.tecnoFibras.tecnoFibras.controller;

import com.tecnoFibras.tecnoFibras.domain.Rol;
import com.tecnoFibras.tecnoFibras.domain.Usuario;
import com.tecnoFibras.tecnoFibras.service.UsuarioService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private static final List<String> ROLES_DISPONIBLES = List.of("CLIENTE", "VENDEDOR", "ADMINISTRADOR");

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var usuarios = usuarioService.getUsuarios();

        // Arma un mapa idUsuario -> nombreRol para mostrarlo en la tabla
        Map<Integer, String> rolesPorUsuario = new HashMap<>();
        for (Usuario u : usuarios) {
            String rol = usuarioService.getRolDeUsuario(u.getIdUsuario())
                    .map(Rol::getNombre).orElse("Sin rol");
            rolesPorUsuario.put(u.getIdUsuario(), rol);
        }

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("rolesPorUsuario", rolesPorUsuario);
        model.addAttribute("totalUsuarios", usuarios.size());
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("rolesDisponibles", ROLES_DISPONIBLES);
        return "/usuario/listado";
    }

    @PostMapping("/guardar")
    public String guardar(Usuario usuario, @RequestParam("nombreRol") String nombreRol,
            RedirectAttributes redirectAttributes) {
        try {
            usuarioService.save(usuario, nombreRol);
            redirectAttributes.addFlashAttribute("todoOk", "Usuario guardado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo guardar el usuario: " + e.getMessage());
        }
        return "redirect:/usuario/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.delete(id);
            redirectAttributes.addFlashAttribute("todoOk", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "No se puede eliminar el usuario. Tiene datos asociados (cotizaciones, roles).");
        }
        return "redirect:/usuario/listado";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Usuario> usuarioOpt = usuarioService.getUsuario(id);
        if (usuarioOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El usuario no existe.");
            return "redirect:/usuario/listado";
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setPassword(""); // nunca se muestra la clave encriptada en el formulario

        String rolActual = usuarioService.getRolDeUsuario(id).map(Rol::getNombre).orElse("CLIENTE");

        model.addAttribute("usuario", usuario);
        model.addAttribute("rolActual", rolActual);
        model.addAttribute("rolesDisponibles", ROLES_DISPONIBLES);
        return "/usuario/modifica";
    }
}
