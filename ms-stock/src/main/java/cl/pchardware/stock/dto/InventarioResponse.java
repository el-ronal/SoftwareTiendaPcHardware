package cl.pchardware.stock.dto;

import lombok.Data;

@Data
public class InventarioResponse {

    private Integer idInventario;
    private Integer idBodega;
    private String nombreBodega;
    private String skuProducto;
    private Integer cantidad;
}
