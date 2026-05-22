package cl.pchardware.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevolucionCreadaEvent {
    private Integer idDevolucion;
    private Integer idPedido;
    private String motivo;
    private String estado;
}
