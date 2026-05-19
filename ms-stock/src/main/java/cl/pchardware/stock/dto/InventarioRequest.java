// InventarioRequest.java y InventarioResponse.java
package cl.pchardware.stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InventarioRequest {
    @NotBlank(message = "El código de la bodega es obligatorio")
    private String codigoBodega;

    @NotBlank(message = "El SKU del producto es obligatorio")
    @Size(max = 30, message = "El SKU no puede superar los 30 caracteres")
    private String skuProducto;

    @NotNull(message = "La cantidad inicial es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer cantidad;
}