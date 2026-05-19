package cl.pchardware.stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InventarioRequest {

    @NotNull(message = "El ID de bodega es obligatorio")
    private Integer idBodega;

    @NotBlank(message = "El SKU es obligatorio")
    @Size(max = 30, message = "El SKU no puede superar los 30 caracteres")
    private String skuProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
    private Integer cantidad;
}
