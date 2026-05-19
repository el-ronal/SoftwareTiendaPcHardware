package cl.pchardware.devoluciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SolicitudDevolucionRequest {

    @NotNull(message = "El ID de pedido es obligatorio")
    private Integer idPedido;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 25)
    private String motivo;

    @Size(max = 20)
    private String estado;
}
