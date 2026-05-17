package cl.pchardware.pedidos.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PedidoRequest {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer idUsuario;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20, message = "El estado no puede superar los 20 caracteres")
    private String estado;

    @NotNull(message = "El total es obligatorio")
    @Min(value = 0, message = "El total debe ser igual o mayor a 0")
    private Integer totalClp;

    @NotNull(message = "Los detalles del pedido son obligatorios")
    private List<DetallePedidoRequest> detalles;
}
