
package com.tecnoFibras.tecnoFibras.service;

import com.tecnoFibras.tecnoFibras.domain.NotaSeguimiento;
import com.tecnoFibras.tecnoFibras.repository.NotaSeguimientoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotaSeguimientoService {

    private final NotaSeguimientoRepository notaSeguimientoRepository;

    public NotaSeguimientoService(NotaSeguimientoRepository notaSeguimientoRepository) {
        this.notaSeguimientoRepository = notaSeguimientoRepository;
    }

    @Transactional(readOnly = true)
    public List<NotaSeguimiento> getNotasPorCotizacion(Integer idCotizacion) {
        return notaSeguimientoRepository.findByIdCotizacionOrderByFechaDesc(idCotizacion);
    }

    @Transactional
    public void save(NotaSeguimiento nota) {
        if (nota.getIdNota() == null) {
            nota.setFecha(LocalDateTime.now());
        }
        notaSeguimientoRepository.save(nota);
    }

    @Transactional
    public void delete(Integer id) {
        notaSeguimientoRepository.deleteById(id);
    }
}
