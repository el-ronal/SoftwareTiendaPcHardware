package cl.pchardware.notificaciones.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MensajeResponse {
    private Integer idMensaje;
    private Integer idUsuario;
    private Integer idPlantilla;
    private String codigoEvento;
    private String estadoMensaje;
    private LocalDateTime fechaGeneracion;
}
