package com.tecnoFibras.tecnoFibras.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "promocion")
public class Promocion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promocion")
    private Integer idPromocion;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "La descripción no puede estar vacía.")
    @Size(max = 200, message = "La descripción no puede tener más de 200 caracteres.")
    private String descripcion;

    @NotNull(message = "El descuento no puede estar vacío.")
    private Double descuento;

    private Boolean activo;

    // Relación muchos-a-muchos: una promoción aplica a varios productos
    // y un producto puede estar en varias promociones.
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producto_promocion",
            joinColumns = @JoinColumn(name = "id_promocion"),
            inverseJoinColumns = @JoinColumn(name = "id_producto")
    )
    private List<Producto> productos = new ArrayList<>();
}
