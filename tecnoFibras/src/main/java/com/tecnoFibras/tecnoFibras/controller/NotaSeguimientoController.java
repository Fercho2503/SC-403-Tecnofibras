
package com.tecnoFibras.tecnoFibras.controller;

import com.tecnoFibras.tecnoFibras.domain.NotaSeguimiento;
import com.tecnoFibras.tecnoFibras.service.NotaSeguimientoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/nota-seguimiento")
public class NotaSeguimientoController {

    private final NotaSeguimientoService notaSeguimientoService;

    public NotaSeguimientoController(NotaSeguimientoService notaSeguimientoService) {
        this.notaSeguimientoService = notaSeguimientoService;
    }

    @GetMapping("/cotizacion/{idCotizacion}")
    public String listado(@PathVariable Integer idCotizacion, Model model) {
        model.addAttribute("notas", notaSeguimientoService.getNotasPorCotizacion(idCotizacion));
        model.addAttribute("idCotizacion", idCotizacion);
        model.addAttribute("nota", new NotaSeguimiento());
        return "nota-seguimiento/listado";
    }

    @PostMapping("/guardar")
    public String guardar(NotaSeguimiento nota, RedirectAttributes redirectAttributes) {
        notaSeguimientoService.save(nota);
        redirectAttributes.addFlashAttribute("todoOk", "Nota de seguimiento agregada.");
        return "redirect:/nota-seguimiento/cotizacion/" + nota.getIdCotizacion();
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer id, @RequestParam Integer idCotizacion,
            RedirectAttributes redirectAttributes) {
        try {
            notaSeguimientoService.delete(id);
            redirectAttributes.addFlashAttribute("todoOk", "Nota eliminada.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar la nota.");
        }
        return "redirect:/nota-seguimiento/cotizacion/" + idCotizacion;
    }
}