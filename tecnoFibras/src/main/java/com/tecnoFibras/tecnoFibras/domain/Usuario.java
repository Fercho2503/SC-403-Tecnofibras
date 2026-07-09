package com.tecnoFibras.tecnoFibras.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "rol", length = 20)
    private String rol;

    @Column(name = "activo")
    private boolean activo;
}
