package com.tecnoFibras.tecnoFibras.controller;

import com.tecnoFibras.tecnoFibras.service.CategoriaService;
import com.tecnoFibras.tecnoFibras.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public CatalogoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String catalogo(Model model) {
        model.addAttribute("productos", productoService.getProductos());
        model.addAttribute("categorias", categoriaService.getCategorias());
        return "catalogo/listado";
    }

    @GetMapping("/categoria/{id}")
    public String porCategoria(@PathVariable Integer id, Model model) {
        model.addAttribute("productos", productoService.getProductosByCategoria(id));
        model.addAttribute("categorias", categoriaService.getCategorias());
        return "catalogo/listado";
    }
}
