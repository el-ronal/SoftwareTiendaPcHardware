package cl.pchardware.pedidos.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HistorialEstadoResponse {
    private Long idHistorial;
    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDateTime fechaCambio;
}
