package cl.pchardware.pedidos.dto;

import cl.pchardware.pedidos.model.Pedido;
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
public class HistorialEstadoRequest {

    @NotNull(message = "El ID del pedido es obligatorio")
    private Integer idPedido;

    private Pedido.EstadoPedido estadoAnterior;

    @NotNull(message = "El estado nuevo es obligatorio")
    private Pedido.EstadoPedido estadoNuevo;
}
