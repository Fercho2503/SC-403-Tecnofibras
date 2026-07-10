package com.tecnoFibras.tecnoFibras.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "cotizacion")
public class Cotizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion")
    private Integer idCotizacion;

    @NotNull(message = "El cliente es obligatorio.")
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private Usuario vendedor;

    private LocalDateTime fecha;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "El estado no puede estar vacío.")
    @Size(max = 30, message = "El estado no puede tener más de 30 caracteres.")
    private String estado;
}
