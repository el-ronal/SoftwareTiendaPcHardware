package cl.pchardware.pedidos.dto;

import lombok.Data;

@Data
public class DetallePedidoResponse {
    private Long idDetalle;
    private String skuProducto;
    private Integer cantidad;
    private Integer precioUnitario;
}
