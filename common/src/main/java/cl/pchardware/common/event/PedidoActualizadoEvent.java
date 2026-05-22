package cl.pchardware.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PedidoActualizadoEvent extends PedidoEvent {

    private String estadoAnterior;

    public PedidoActualizadoEvent(Long idPedido, Integer idUsuario,
                                   String estadoAnterior, String estadoNuevo,
                                   Integer totalClp) {
        super(idPedido, idUsuario, estadoNuevo, totalClp);
        this.estadoAnterior = estadoAnterior;
    }
}
