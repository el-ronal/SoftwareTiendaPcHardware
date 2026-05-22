package cl.pchardware.soporte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MensajeTicketRequest {

    @NotNull(message = "El ID de ticket es obligatorio")
    private Integer idTicket;

    @NotBlank(message = "El remitente es obligatorio")
    @Size(max = 20)
    private String remitente;

    @NotBlank(message = "El contenido es obligatorio")
    @Size(max = 500)
    private String contenido;
}
