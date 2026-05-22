package cl.pchardware.stock.dto;
import lombok.Data;

@Data
public class InventarioResponse {
    private Long idInventario;
    private String skuProducto;
    private Integer cantidad;
    private BodegaResponse bodega; // Relación aplanada
}