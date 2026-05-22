package cl.pchardware.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PedidoCreadoEvent extends PedidoEvent {

    public PedidoCreadoEvent(Long idPedido, Integer idUsuario, String estado, Integer totalClp) {
        super(idPedido, idUsuario, estado, totalClp);
    }
}
