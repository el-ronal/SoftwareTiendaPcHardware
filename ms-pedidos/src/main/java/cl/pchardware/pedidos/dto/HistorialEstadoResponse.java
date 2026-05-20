package cl.pchardware.pedidos.dto;

import java.time.LocalDateTime;

import cl.pchardware.pedidos.model.Pedido;
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
public class HistorialEstadoResponse {

    private Integer idHistorial;
    private Integer idPedido;
    private Pedido.EstadoPedido estadoAnterior;
    private Pedido.EstadoPedido estadoNuevo;
    private LocalDateTime fechaCambio;
}
