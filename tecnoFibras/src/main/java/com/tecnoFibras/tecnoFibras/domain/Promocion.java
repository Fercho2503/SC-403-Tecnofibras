package com.tecnoFibras.tecnoFibras.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(nullable = false, length = 30, unique = true)
    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(max = 30, message = "El nombre de usuario no puede tener más de 30 caracteres.")
    private String username;

    @Column(nullable = false, length = 512)
    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(max = 512, message = "La contraseña es demasiado larga.")
    private String password;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 30, message = "El nombre no puede tener más de 30 caracteres.")
    private String nombre;

    @Column(nullable = false, length = 60)
    @NotBlank(message = "Los apellidos no pueden estar vacíos.")
    @Size(max = 60, message = "Los apellidos no pueden tener más de 60 caracteres.")
    private String apellidos;

    @Column(length = 50)
    @Email(message = "El formato del correo es inválido.")
    @Size(max = 50, message = "El correo no puede tener más de 50 caracteres.")
    private String correo;

    @Column(length = 25)
    @Size(max = 25, message = "El teléfono no puede tener más de 25 caracteres.")
    private String telefono;

    @Column(name = "ruta_imagen", length = 1024)
    private String rutaImagen;

    private Boolean activo;
}
