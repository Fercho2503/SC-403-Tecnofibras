
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
@Table(name = "nota_seguimiento")
public class NotaSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Integer idNota;

    @NotNull(message = "El ID de la cotización es obligatorio.")
    @Column(name = "id_cotizacion")
    private Integer idCotizacion;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "El comentario no puede estar vacío.")
    @Size(max = 500, message = "El comentario no puede tener más de 500 caracteres.")
    private String comentario;

    private LocalDateTime fecha;
}
