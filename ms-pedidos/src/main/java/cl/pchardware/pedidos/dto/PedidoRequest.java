package cl.pchardware.pedidos.dto;

import cl.pchardware.pedidos.model.Pedido;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoRequest {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer idUsuario;

    @NotNull(message = "El estado es obligatorio")
    private Pedido.EstadoPedido estado;

    @NotNull(message = "El total es obligatorio")
    @Min(value = 0, message = "El total no puede ser negativo")
    private Integer totalClp;
}
