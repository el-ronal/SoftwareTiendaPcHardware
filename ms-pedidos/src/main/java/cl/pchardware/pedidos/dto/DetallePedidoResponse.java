package cl.pchardware.pedidos.dto;

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
public class DetallePedidoResponse {

    private Integer idDetalle;
    private Integer idPedido;
    private String skuProducto;
    private Integer cantidad;
    private Integer precioUnitario;
}
