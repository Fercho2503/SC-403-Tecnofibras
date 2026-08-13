
package com.tecnoFibras.tecnoFibras.repository;
import com.tecnoFibras.tecnoFibras.domain.DetalleCotizacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCotizacionRepository extends JpaRepository<DetalleCotizacion, Integer> {
    
    // Clave para recuperar todos los productos que pertenecen a una misma cotización
    List<DetalleCotizacion> findByIdCotizacion(Integer idCotizacion);
}

