/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnoFibras.tecnoFibras.repository;

import com.tecnoFibras.tecnoFibras.domain.Promocion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Integer> {
    
    // Para obtener las ofertas y descuentos que están vigentes actualmente
    List<Promocion> findByActivoTrue();
}
