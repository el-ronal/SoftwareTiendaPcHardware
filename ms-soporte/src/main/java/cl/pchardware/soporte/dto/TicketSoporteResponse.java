package cl.pchardware.soporte.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TicketSoporteResponse {
    private Integer idTicket;
    private Integer idUsuario;
    private String categoria;
    private String estado;
    private LocalDateTime fechaApertura;
}
