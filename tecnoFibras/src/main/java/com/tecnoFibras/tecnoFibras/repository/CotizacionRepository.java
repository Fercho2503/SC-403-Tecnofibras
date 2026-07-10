package com.tecnoFibras.tecnoFibras.repository;

import com.tecnoFibras.tecnoFibras.domain.Cotizacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {
    
    // Para que un cliente vea su propio historial de solicitudes
    List<Cotizacion> findByIdCliente(Integer idCliente);
    
    // Para que un vendedor gestione las cotizaciones que tiene asignadas
    List<Cotizacion> findByIdVendedor(Integer idVendedor);
    
    // Para filtrar las cotizaciones por su estado ('Pendiente', 'En Proceso', 'Finalizada')
    List<Cotizacion> findByEstado(String estado);
}
