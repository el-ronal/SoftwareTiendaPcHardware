package cl.pchardware.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarantiaCreadaEvent {
    private Integer idTicket;
    private Integer idPedido;
    private String skuProducto;
    private String motivoCliente;
    private String estado;
}
