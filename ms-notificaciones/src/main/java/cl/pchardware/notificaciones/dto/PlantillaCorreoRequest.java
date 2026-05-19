package cl.pchardware.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlantillaCorreoRequest {

    @NotBlank(message = "El código de evento es obligatorio")
    @Size(max = 30)
    private String codigoEvento;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 100)
    private String asunto;

    @NotBlank(message = "El cuerpo HTML es obligatorio")
    @Size(max = 255)
    private String cuerpoHtml;
}
