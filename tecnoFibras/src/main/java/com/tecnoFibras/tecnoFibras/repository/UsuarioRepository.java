package com.tecnoFibras.tecnoFibras.repository;

import com.tecnoFibras.tecnoFibras.domain.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
   // Este método es obligatorio y vital para cuando configures Spring Security (Login)
    Optional<Usuario> findByUsername(String username);
    
    // Para validar si un correo ya está registrado
    Optional<Usuario> findByCorreo(String correo);
}
