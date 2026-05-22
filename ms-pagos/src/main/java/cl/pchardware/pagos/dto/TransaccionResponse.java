package cl.pchardware.pagos.dto;

import cl.pchardware.pagos.model.Transaccion;
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
public class TransaccionResponse {

    private Integer idTransaccion;
    private Integer idPedido;
    private Integer idMetodo;
    private Integer montoClp;
    private Transaccion.EstadoTransaccion estado;
}
