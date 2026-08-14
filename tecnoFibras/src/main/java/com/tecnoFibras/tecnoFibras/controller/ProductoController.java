package com.tecnoFibras.tecnoFibras.controller;

import com.tecnoFibras.tecnoFibras.domain.Producto;
import com.tecnoFibras.tecnoFibras.service.CategoriaService;
import com.tecnoFibras.tecnoFibras.service.ProductoService;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getTodos();
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categoriaService.getCategorias());
        model.addAttribute("producto", new Producto());
        return "/producto/listado";
    }

    @PostMapping("/guardar")
    public String guardar(Producto producto, RedirectAttributes redirectAttributes) {
        try {
            productoService.save(producto);
            redirectAttributes.addFlashAttribute("todoOk", "Producto guardado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo guardar el producto: " + e.getMessage());
        }
        return "redirect:/producto/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            productoService.delete(id);
            redirectAttributes.addFlashAttribute("todoOk", "Producto eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar el producto. Tiene datos asociados (cotizaciones, promociones).");
        }
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Producto> productoOpt = productoService.getProducto(id);
        if (productoOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El producto no existe.");
            return "redirect:/producto/listado";
        }
        model.addAttribute("producto", productoOpt.get());
        model.addAttribute("categorias", categoriaService.getCategorias());
        return "/producto/modifica";
    }
}
