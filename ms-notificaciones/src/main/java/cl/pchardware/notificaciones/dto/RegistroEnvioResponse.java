package cl.pchardware.notificaciones.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RegistroEnvioResponse {
    
    private Integer idRegistro;
    private Integer idMensaje; // Recibe el valor de mensaje.idMensaje
    private String proveedorSmtp;
    private Integer intentos;
    private LocalDateTime fechaUltimoIntento;
}