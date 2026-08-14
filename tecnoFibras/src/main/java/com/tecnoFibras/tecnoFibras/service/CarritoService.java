package com.tecnoFibras.tecnoFibras.service;

import com.tecnoFibras.tecnoFibras.domain.*;
import com.tecnoFibras.tecnoFibras.repository.CotizacionRepository;
import com.tecnoFibras.tecnoFibras.repository.DetalleCotizacionRepository;
import com.tecnoFibras.tecnoFibras.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarritoService {

    private static final String ATTRIBUTE_CARRITO = "carritoCotizacion";

    private final ProductoRepository productoRepository;
    private final CotizacionRepository cotizacionRepository;
    private final DetalleCotizacionRepository detalleCotizacionRepository;

    public CarritoService(ProductoRepository productoRepository,
            CotizacionRepository cotizacionRepository,
            DetalleCotizacionRepository detalleCotizacionRepository) {
        this.productoRepository = productoRepository;
        this.cotizacionRepository = cotizacionRepository;
        this.detalleCotizacionRepository = detalleCotizacionRepository;
    }

    // --- Gestión de sesión ---
    public List<Item> obtenerCarrito(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<Item> carrito = (List<Item>) session.getAttribute(ATTRIBUTE_CARRITO);
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        return carrito;
    }

    public void guardarCarrito(HttpSession session, List<Item> carrito) {
        session.setAttribute(ATTRIBUTE_CARRITO, carrito);
    }

    public void agregarProducto(List<Item> carrito, Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado."));

        Optional<Item> itemExistente = carrito.stream()
                .filter(i -> i.getProducto().getIdProducto().equals(idProducto))
                .findFirst();

        if (itemExistente.isPresent()) {
            itemExistente.get().setCantidad(itemExistente.get().getCantidad() + 1);
        } else {
            carrito.add(new Item(producto, 1));
        }
    }

    public void actualizarCantidad(List<Item> carrito, Integer idProducto, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            eliminarItem(carrito, idProducto);
            return;
        }
        carrito.stream()
                .filter(i -> i.getProducto().getIdProducto().equals(idProducto))
                .findFirst()
                .ifPresent(item -> item.setCantidad(nuevaCantidad));
    }

    public void eliminarItem(List<Item> carrito, Integer idProducto) {
        carrito.removeIf(item -> item.getProducto().getIdProducto().equals(idProducto));
    }

    public void limpiarCarrito(HttpSession session) {
        session.removeAttribute(ATTRIBUTE_CARRITO);
    }

    // --- Envío de la cotización (equivalente a "facturar" en tienda) ---
    @Transactional
    public Cotizacion enviarCotizacion(List<Item> carrito, Usuario cliente) {
        if (carrito == null || carrito.isEmpty()) {
            throw new RuntimeException("No hay productos agregados a la cotización.");
        }

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setUsuario(cliente);
        cotizacion.setVendedor(null); // se asigna después, cuando un vendedor la toma
        cotizacion.setFecha(LocalDateTime.now());
        cotizacion.setEstado("Pendiente");
        cotizacion = cotizacionRepository.save(cotizacion);

        for (Item item : carrito) {
            DetalleCotizacion detalle = new DetalleCotizacion();
            detalle.setIdCotizacion(cotizacion.getIdCotizacion());
            detalle.setIdProducto(item.getProducto().getIdProducto());
            detalle.setCantidad(item.getCantidad());
            detalleCotizacionRepository.save(detalle);
        }

        return cotizacion;
    }
}
