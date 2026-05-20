package cl.pchardware.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PerfilRequest {

    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT inválido (ej: 12345678-9)")
    @Size(max = 12, message = "El RUT no puede superar los 12 caracteres")
    private String rut;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 100, message = "El nombre completo no puede superar los 100 caracteres")
    private String nombreCompleto;

    @Pattern(regexp = "^(\\+?[0-9]{9,15})?$", message = "El teléfono solo puede contener números y opcionalmente un '+' al inicio")
    @Size(max = 15, message = "El teléfono no puede superar los 15 caracteres")
    private String telefono;
}