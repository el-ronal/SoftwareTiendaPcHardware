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
public class PedidoResponse {

    private Integer idPedido;
    private Integer idUsuario;
    private LocalDateTime fechaCreacion;
    private Pedido.EstadoPedido estado;
    private Integer totalClp;
}
