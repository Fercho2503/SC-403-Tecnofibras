package com.tecnoFibras.tecnoFibras.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "ruta")
public class Ruta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private Integer idRuta;

    private String ruta;

    @Column(name = "requiere_rol")
    private boolean requiereRol;

    // Texto separado por comas, ej: "VENDEDOR,ADMINISTRADOR"
    @Column(name = "roles_requeridos")
    private String rolesRequeridos;
}
