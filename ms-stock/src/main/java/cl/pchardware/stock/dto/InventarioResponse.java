package cl.pchardware.stock.dto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class InventarioResponse extends RepresentationModel<InventarioResponse> {
    private Long idInventario;
    private String skuProducto;
    private Integer cantidad;
    private BodegaResponse bodega; // Relación aplanada
}