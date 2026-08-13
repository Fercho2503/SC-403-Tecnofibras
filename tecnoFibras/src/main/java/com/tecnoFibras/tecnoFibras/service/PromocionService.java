package com.tecnoFibras.tecnoFibras.service;

import com.tecnoFibras.tecnoFibras.domain.Producto;
import com.tecnoFibras.tecnoFibras.domain.Promocion;
import com.tecnoFibras.tecnoFibras.repository.ProductoRepository;
import com.tecnoFibras.tecnoFibras.repository.PromocionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PromocionService {

    private final PromocionRepository promocionRepository;
    private final ProductoRepository productoRepository;

    public PromocionService(PromocionRepository promocionRepository,  ProductoRepository productoRepository) {
        this.promocionRepository = promocionRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<Promocion> getPromociones() {
        return promocionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Promocion> getPromocion(Integer id) {
        return promocionRepository.findById(id);
    }

    @Transactional
    public void save(Promocion promocion, List<Integer> productosIds) {
        List<Producto> productos = new ArrayList<>();
        if (productosIds != null && !productosIds.isEmpty()) {
            productos = productoRepository.findAllById(productosIds);
        }
        promocion.setProductos(productos);
        promocionRepository.save(promocion);
    }

    @Transactional
    public void delete(Integer id) {
        promocionRepository.deleteById(id);
    }
}
