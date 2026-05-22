package cl.pchardware.armado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrdenEnsambleRequest {

    @NotNull(message = "El ID de pedido es obligatorio")
    private Integer idPedido;

    @NotNull(message = "El ID del técnico es obligatorio")
    private Integer idTecnico;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20)
    private String estado;
}
