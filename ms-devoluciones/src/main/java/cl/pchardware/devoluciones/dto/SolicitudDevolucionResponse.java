package cl.pchardware.devoluciones.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SolicitudDevolucionResponse {
    private Integer idDevolucion;
    private Integer idPedido;
    private String motivo;
    private String estado;
    private LocalDateTime fechaSolicitud;
}
