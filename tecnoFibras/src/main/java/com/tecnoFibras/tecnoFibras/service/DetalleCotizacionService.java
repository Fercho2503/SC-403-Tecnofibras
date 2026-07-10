package com.tecnoFibras.tecnoFibras.service;

import com.tecnoFibras.tecnoFibras.domain.DetalleCotizacion;
import com.tecnoFibras.tecnoFibras.repository.DetalleCotizacionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetalleCotizacionService {

    private final DetalleCotizacionRepository detalleCotizacionRepository;

    public DetalleCotizacionService(DetalleCotizacionRepository detalleCotizacionRepository) {
        this.detalleCotizacionRepository = detalleCotizacionRepository;
    }

    @Transactional(readOnly = true)
    public List<DetalleCotizacion> getDetalles() {
        return detalleCotizacionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DetalleCotizacion> getDetalle(Integer id) {
        return detalleCotizacionRepository.findById(id);
    }

    @Transactional
    public void save(DetalleCotizacion detalleCotizacion) {
        detalleCotizacionRepository.save(detalleCotizacion);
    }

    @Transactional
    public void delete(Integer id) {
        detalleCotizacionRepository.deleteById(id);
    }
}
