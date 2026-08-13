package com.tecnoFibras.tecnoFibras.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @NotNull(message = "La categoría es obligatoria.")
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "La descripción no puede estar vacía.")
    @Size(max = 100, message = "La descripción no puede tener más de 100 caracteres.")
    private String descripcion;

    @Column(length = 70)
    @Size(max = 70, message = "Las medidas no pueden tener más de 70 caracteres.")
    private String medidas;

    @Column(length = 40)
    @Size(max = 40, message = "El galonaje no puede tener más de 40 caracteres.")
    private String galonaje;

    @Column(length = 70)
    @Size(max = 70, message = "La capacidad no puede tener más de 70 caracteres.")
    private String capacidad;

    @NotNull(message = "El precio es obligatorio.")
    private Double precio;

    @Column(name = "ruta_imagen", length = 1024)
    @Size(max = 1024, message = "La ruta de la imagen es demasiado larga.")
    private String rutaImagen;

    private Boolean activo;
}
