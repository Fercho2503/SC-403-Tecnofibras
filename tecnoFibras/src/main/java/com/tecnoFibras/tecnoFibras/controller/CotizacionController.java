package com.tecnoFibras.tecnoFibras.controller;

import com.tecnoFibras.tecnoFibras.domain.Cotizacion;
import com.tecnoFibras.tecnoFibras.domain.DetalleCotizacion;
import com.tecnoFibras.tecnoFibras.domain.Item;
import com.tecnoFibras.tecnoFibras.domain.Usuario;
import com.tecnoFibras.tecnoFibras.service.CarritoService;
import com.tecnoFibras.tecnoFibras.service.CotizacionService;
import com.tecnoFibras.tecnoFibras.service.DetalleCotizacionService;
import com.tecnoFibras.tecnoFibras.service.ProductoService;
import com.tecnoFibras.tecnoFibras.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import com.tecnoFibras.tecnoFibras.domain.Producto;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cotizacion")
public class CotizacionController {

    private final CarritoService carritoService;
    private final CotizacionService cotizacionService;
    private final DetalleCotizacionService detalleCotizacionService;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;

    public CotizacionController(CarritoService carritoService, CotizacionService cotizacionService,
            DetalleCotizacionService detalleCotizacionService, UsuarioService usuarioService,
            ProductoService productoService) {
        this.carritoService = carritoService;
        this.cotizacionService = cotizacionService;
        this.detalleCotizacionService = detalleCotizacionService;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    private Usuario usuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return usuarioService.getUsuarioByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }

    @GetMapping("/carrito")
    public String carrito(HttpSession session, Model model) {
        List<Item> carrito = carritoService.obtenerCarrito(session);
        model.addAttribute("carritoItems", carrito);
        return "cotizacion/carrito";
    }

    @PostMapping("/agregar")
    public String agregar(@RequestParam Integer idProducto, HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            List<Item> carrito = carritoService.obtenerCarrito(session);
            carritoService.agregarProducto(carrito, idProducto);
            carritoService.guardarCarrito(session, carrito);
            redirectAttributes.addFlashAttribute("todoOk", "Producto agregado a tu cotización.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo agregar el producto: " + e.getMessage());
        }
        return "redirect:/catalogo";
    }

    @PostMapping("/actualizar")
    public String actualizar(@RequestParam Integer idProducto, @RequestParam int cantidad,
            HttpSession session) {
        List<Item> carrito = carritoService.obtenerCarrito(session);
        carritoService.actualizarCantidad(carrito, idProducto, cantidad);
        carritoService.guardarCarrito(session, carrito);
        return "redirect:/cotizacion/carrito";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idProducto, HttpSession session) {
        List<Item> carrito = carritoService.obtenerCarrito(session);
        carritoService.eliminarItem(carrito, idProducto);
        carritoService.guardarCarrito(session, carrito);
        return "redirect:/cotizacion/carrito";
    }

    @PostMapping("/enviar")
    public String enviar(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            List<Item> carrito = carritoService.obtenerCarrito(session);
            Cotizacion cotizacion = carritoService.enviarCotizacion(carrito, usuarioActual());
            carritoService.limpiarCarrito(session);
            redirectAttributes.addFlashAttribute("todoOk",
                    "¡Cotización enviada! Número: " + cotizacion.getIdCotizacion() + ". Un vendedor te contactará pronto.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo enviar la cotización: " + e.getMessage());
            return "redirect:/cotizacion/carrito";
        }
        return "redirect:/cotizacion/mis";
    }

    @GetMapping("/mis")
    public String misCotizaciones(Model model) {
        Usuario cliente = usuarioActual();
        List<Cotizacion> cotizaciones = cotizacionService.getCotizaciones().stream()
                .filter(c -> c.getUsuario().getIdUsuario().equals(cliente.getIdUsuario()))
                .toList();
        model.addAttribute("cotizaciones", cotizaciones);
        return "cotizacion/historial";
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var cotizaciones = cotizacionService.getCotizaciones();
        model.addAttribute("cotizaciones", cotizaciones);
        model.addAttribute("totalCotizaciones", cotizaciones.size());
        return "cotizacion/listado";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        var cotizacionOpt = cotizacionService.getCotizacion(id);
        if (cotizacionOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "La cotización no existe.");
            return "redirect:/cotizacion/listado";
        }
        Cotizacion cotizacion = cotizacionOpt.get();

        List<DetalleCotizacion> detalles = detalleCotizacionService.getDetalles().stream()
                .filter(d -> d.getIdCotizacion().equals(id))
                .toList();

        Map<Integer, Producto> productosMap = productoService.getTodos().stream()
                .collect(Collectors.toMap(Producto::getIdProducto, p -> p));

        model.addAttribute("cotizacion", cotizacion);
        model.addAttribute("detalles", detalles);
        model.addAttribute("productosMap", productosMap);
        return "cotizacion/detalle";
    }

    @PostMapping("/actualizarEstado")
    public String actualizarEstado(@RequestParam Integer idCotizacion, @RequestParam String estado,
            RedirectAttributes redirectAttributes) {
        var cotizacionOpt = cotizacionService.getCotizacion(idCotizacion);
        if (cotizacionOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "La cotización no existe.");
            return "redirect:/cotizacion/listado";
        }
        Cotizacion cotizacion = cotizacionOpt.get();
        cotizacion.setEstado(estado);
        if (cotizacion.getVendedor() == null) {
            cotizacion.setVendedor(usuarioActual());
        }
        cotizacionService.save(cotizacion);
        redirectAttributes.addFlashAttribute("todoOk", "Cotización actualizada.");
        return "redirect:/cotizacion/detalle/" + idCotizacion;
    }
}
