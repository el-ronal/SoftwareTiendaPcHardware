package cl.pchardware.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MensajeRequest {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer idUsuario;

    @NotNull(message = "El ID de plantilla es obligatorio")
    private Integer idPlantilla;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20)
    private String estadoMensaje;
}
