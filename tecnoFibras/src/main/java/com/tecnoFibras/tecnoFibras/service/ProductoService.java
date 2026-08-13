package com.tecnoFibras.tecnoFibras.service;

import com.tecnoFibras.tecnoFibras.domain.Producto;
import com.tecnoFibras.tecnoFibras.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<Producto> getProductos() {
        return productoRepository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public List<Producto> getProductosByCategoria(Integer categoriaId) {
        return productoRepository.findByCategoria_IdCategoria(categoriaId);
    }

    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Integer id) {
        return productoRepository.findById(id);
    }

    @Transactional
    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    @Transactional
    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }
}
