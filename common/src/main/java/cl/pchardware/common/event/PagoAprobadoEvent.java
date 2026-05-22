package cl.pchardware.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoAprobadoEvent {
    private Integer idTransaccion;
    private Integer idPedido;
    private Integer idUsuario;
    private Integer montoClp;
    private String metodoPago;
}
