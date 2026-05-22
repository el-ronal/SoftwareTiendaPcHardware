package cl.pchardware.stock.dto;

import lombok.Data;

@Data
public class DetallePedido {
    private String skuProducto;
    private Integer cantidad;
}