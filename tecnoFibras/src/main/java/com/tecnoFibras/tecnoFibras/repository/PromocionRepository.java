/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnoFibras.tecnoFibras.repository;

import com.tecnoFibras.tecnoFibras.domain.Promocion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Integer> {

    List<Promocion> findByActivoTrue();

    // Trae la promoción junto con sus productos ya cargados (evita LazyInitializationException)
    @Query("SELECT p FROM Promocion p LEFT JOIN FETCH p.productos WHERE p.idPromocion = :id")
    Optional<Promocion> findByIdConProductos(@Param("id") Integer id);
}
