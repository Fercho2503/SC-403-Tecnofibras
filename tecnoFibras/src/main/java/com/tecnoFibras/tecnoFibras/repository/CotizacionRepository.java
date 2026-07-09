package com.tecnoFibras.tecnoFibras.repository;

import com.tecnoFibras.tecnoFibras.domain.Cotizacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {
    List<Cotizacion> findByUsuarioId(Integer usuarioId);
    List<Cotizacion> findByVendedorId(Integer vendedorId);
}
