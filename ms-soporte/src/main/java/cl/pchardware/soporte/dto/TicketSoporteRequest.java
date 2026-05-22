package cl.pchardware.soporte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TicketSoporteRequest {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer idUsuario;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 30)
    private String categoria;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20)
    private String estado;
}
