package com.tecnoFibras.tecnoFibras.repository;

import com.tecnoFibras.tecnoFibras.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByActivoTrue();

    List<Producto> findByCategoria_IdCategoria(Integer categoriaId);
    
      // Filtro combinado del catálogo: categoría y rango de precio son opcionales
    @Query("SELECT p FROM Producto p WHERE p.activo = true "
            + "AND (:categoriaId IS NULL OR p.categoria.idCategoria = :categoriaId) "
            + "AND (:precioMin IS NULL OR p.precio >= :precioMin) "
            + "AND (:precioMax IS NULL OR p.precio <= :precioMax)")
    List<Producto> findByFiltros(@Param("categoriaId") Integer categoriaId,
            @Param("precioMin") Double precioMin,
            @Param("precioMax") Double precioMax);
}

