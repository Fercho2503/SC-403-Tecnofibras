package com.tecnoFibras.tecnoFibras.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(nullable = false, length = 40)
    @NotBlank(message = "El nombre del rol no puede estar vacío.")
    @Size(max = 40, message = "El nombre del rol no puede tener más de 40 caracteres.")
    private String nombre;

    @NotNull(message = "El ID de usuario es obligatorio.")
    @Column(name = "id_usuario")
    private Integer idUsuario;
}
