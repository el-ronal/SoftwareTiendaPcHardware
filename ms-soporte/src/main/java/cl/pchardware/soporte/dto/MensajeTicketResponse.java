package cl.pchardware.soporte.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MensajeTicketResponse {
    private Integer idMensaje;
    private Integer idTicket;
    private String remitente;
    private String contenido;
    private LocalDateTime fechaEnvio;
}
