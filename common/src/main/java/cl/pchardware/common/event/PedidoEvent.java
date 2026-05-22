package cl.pchardware.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase base para todos los eventos de Pedido.
 * Contiene los campos comunes que todos los consumidores necesitan.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PedidoEvent {
    private Long idPedido;
    private Integer idUsuario;
    private String estado;
    private Integer totalClp;
}
