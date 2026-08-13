
package com.tecnoFibras.tecnoFibras.controller;
import com.tecnoFibras.tecnoFibras.domain.Promocion;
import com.tecnoFibras.tecnoFibras.service.ProductoService;
import com.tecnoFibras.tecnoFibras.service.PromocionService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/promocion")
public class PromocionController {

    private final PromocionService promocionService;
    private final ProductoService productoService;

    public PromocionController(PromocionService promocionService, ProductoService productoService) {
        this.promocionService = promocionService;
        this.productoService = productoService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var promociones = promocionService.getPromociones();
        model.addAttribute("promociones", promociones);
        model.addAttribute("totalPromociones", promociones.size());
        model.addAttribute("promocion", new Promocion());
        model.addAttribute("productos", productoService.getProductos());
        return "/promocion/listado";
    }

    @PostMapping("/guardar")
    public String guardar(Promocion promocion,
            @RequestParam(name = "productosIds", required = false) List<Integer> productosIds,
            RedirectAttributes redirectAttributes) {
        promocionService.save(promocion, productosIds);
        redirectAttributes.addFlashAttribute("todoOk", "Promoción guardada correctamente.");
        return "redirect:/promocion/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            promocionService.delete(id);
            redirectAttributes.addFlashAttribute("todoOk", "Promoción eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar la promoción.");
        }
        return "redirect:/promocion/listado";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Promocion> promocionOpt = promocionService.getPromocion(id);
        if (promocionOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "La promoción no existe.");
            return "redirect:/promocion/listado";
        }
        Promocion promocion = promocionOpt.get();
        model.addAttribute("promocion", promocion);
        model.addAttribute("productos", productoService.getProductos());
        model.addAttribute("productosSeleccionados",
                promocion.getProductos().stream().map(p -> p.getIdProducto()).toList());
        return "/promocion/modifica";
    }
}