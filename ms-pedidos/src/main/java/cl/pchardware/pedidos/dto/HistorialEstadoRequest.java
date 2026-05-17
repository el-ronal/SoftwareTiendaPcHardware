package cl.pchardware.pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HistorialEstadoRequest {

    @Size(max = 20, message = "El estado anterior no puede superar los 20 caracteres")
    private String estadoAnterior;

    @NotBlank(message = "El estado nuevo es obligatorio")
    @Size(max = 20, message = "El estado nuevo no puede superar los 20 caracteres")
    private String estadoNuevo;
}
