package cl.pchardware.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RolRequest {

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 30, message = "El nombre no puede superar los 30 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no puede superar los 100 caracteres")
    private String descripcion;
}