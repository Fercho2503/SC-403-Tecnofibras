package com.tecnoFibras.tecnoFibras.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "medidas", length = 100)
    private String medidas;

    @Column(name = "galonaje")
    private Double galonaje;

    @Column(name = "capacidad", length = 100)
    private String capacidad;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "ruta_imagen", length = 500)
    private String rutaImagen;

    @Column(name = "activo")
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
