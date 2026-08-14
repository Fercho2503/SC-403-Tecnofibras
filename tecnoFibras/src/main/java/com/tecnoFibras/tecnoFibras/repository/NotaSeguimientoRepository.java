
package com.tecnoFibras.tecnoFibras.repository;

import com.tecnoFibras.tecnoFibras.domain.NotaSeguimiento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaSeguimientoRepository extends JpaRepository<NotaSeguimiento, Integer> {

    List<NotaSeguimiento> findByIdCotizacionOrderByFechaDesc(Integer idCotizacion);
}