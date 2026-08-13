package com.tecnoFibras.tecnoFibras.controller;

import com.tecnoFibras.tecnoFibras.service.CategoriaService;
import com.tecnoFibras.tecnoFibras.service.ProductoService;
import com.tecnoFibras.tecnoFibras.service.PromocionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final PromocionService promocionService;

    public CatalogoController(ProductoService productoService, CategoriaService categoriaService, PromocionService promocionService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.promocionService = promocionService;
    }

    @GetMapping
    public String catalogo(@RequestParam(required = false) Integer categoriaId,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            Model model) {
        model.addAttribute("productos", productoService.getProductosFiltrados(categoriaId, precioMin, precioMax));
        model.addAttribute("categorias", categoriaService.getCategorias());
        model.addAttribute("categoriaSeleccionada", categoriaId != null ? categoriaId : 0);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("descuentos", promocionService.getMejoresDescuentosPorProducto());
        return "catalogo/listado";
    }

    @GetMapping("/producto/{id}")
    public String detalle(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        var productoOpt = productoService.getProducto(id);
        if (productoOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El producto no existe.");
            return "redirect:/catalogo";
        }
        model.addAttribute("producto", productoOpt.get());
        model.addAttribute("descuentos", promocionService.getMejoresDescuentosPorProducto());
        return "catalogo/detalle";
    }
}
