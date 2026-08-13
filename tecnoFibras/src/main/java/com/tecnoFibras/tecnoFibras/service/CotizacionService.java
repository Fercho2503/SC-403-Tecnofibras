package com.tecnoFibras.tecnoFibras.service;

import com.tecnoFibras.tecnoFibras.domain.Cotizacion;
import com.tecnoFibras.tecnoFibras.repository.CotizacionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CotizacionService {

    private final CotizacionRepository cotizacionRepository;

    public CotizacionService(CotizacionRepository cotizacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
    }

    @Transactional(readOnly = true)
    public List<Cotizacion> getCotizaciones() {
        return cotizacionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Cotizacion> getCotizacion(Integer id) {
        return cotizacionRepository.findById(id);
    }

    @Transactional
    public void save(Cotizacion cotizacion) {
        cotizacionRepository.save(cotizacion);
    }

    @Transactional
    public void delete(Integer id) {
        cotizacionRepository.deleteById(id);
    }
}
