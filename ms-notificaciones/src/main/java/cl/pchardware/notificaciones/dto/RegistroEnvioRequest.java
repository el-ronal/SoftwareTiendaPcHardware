package cl.pchardware.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistroEnvioRequest {
    
    @NotNull
    private Integer idMensaje; // Se mapeará a mensaje.idMensaje

    @NotBlank
    private String proveedorSmtp;

    private Integer intentos;
}