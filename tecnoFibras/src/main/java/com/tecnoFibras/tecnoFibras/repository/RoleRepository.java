package com.tecnoFibras.tecnoFibras.repository;

import com.tecnoFibras.tecnoFibras.domain.role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<role, Integer> {
    
    // Permite encontrar todos los roles asignados a un usuario específico (Cliente, Vendedor, Admin)
    List<role> findByIdUsuario(Integer idUsuario);
}
