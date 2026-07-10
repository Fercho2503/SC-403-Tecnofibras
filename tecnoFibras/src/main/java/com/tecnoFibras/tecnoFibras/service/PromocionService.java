package com.tecnoFibras.tecnoFibras.service;

import com.tecnoFibras.tecnoFibras.domain.Promocion;
import com.tecnoFibras.tecnoFibras.repository.PromocionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PromocionService {

    private final PromocionRepository promocionRepository;

    public PromocionService(PromocionRepository promocionRepository) {
        this.promocionRepository = promocionRepository;
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
    public void save(Promocion promocion) {
        promocionRepository.save(promocion);
    }

    @Transactional
    public void delete(Integer id) {
        promocionRepository.deleteById(id);
    }
}
