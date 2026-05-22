package cl.pchardware.tasacion.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SolicitudTasacionResponse {
    private Integer idSolicitud;
    private Integer idUsuario;
    private String hardwareDescripcion;
    private String estadoSolicitud;
    private LocalDateTime fechaIngreso;
}
