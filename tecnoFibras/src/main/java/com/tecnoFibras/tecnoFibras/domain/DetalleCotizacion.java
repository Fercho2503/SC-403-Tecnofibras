package com.tecnoFibras.tecnoFibras.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_cotizacion")
public class DetalleCotizacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @NotNull(message = "El ID de la cotización es obligatorio.")
    @Column(name = "id_cotizacion")
    private Integer idCotizacion;

    @NotNull(message = "El ID del producto es obligatorio.")
    @Column(name = "id_producto")
    private Integer idProducto;

    @NotNull(message = "La cantidad no puede estar vacía.")
    @Min(value = 1, message = "La cantidad mínima debe ser 1.")
    private Integer cantidad;
}
