package cl.pchardware.usuarios.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequest {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(max = 50, message = "La contraseña no puede superar los 50 caracteres")
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    private String rol; // Aquí esperamos el nombre en texto, ej: "CLIENTE"

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO|BANEADO", message = "El estado debe ser ACTIVO, INACTIVO o BANEADO")
    private String estado;

    @Valid
    @NotNull(message = "Los datos del perfil son obligatorios")
    private PerfilRequest perfil;
}