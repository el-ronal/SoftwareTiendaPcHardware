package cl.pchardware.tasacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SolicitudTasacionRequest {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer idUsuario;

    @NotBlank(message = "La descripción del hardware es obligatoria")
    @Size(max = 255)
    private String hardwareDescripcion;

    @NotBlank(message = "El estado es obligatorio")
    private String estadoSolicitud;
}
