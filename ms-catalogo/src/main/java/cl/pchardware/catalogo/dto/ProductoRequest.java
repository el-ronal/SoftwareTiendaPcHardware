package cl.pchardware.catalogo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductoRequest {

    @NotNull(message = "El ID de marca es obligatorio")
    private Integer idMarca;

    @NotNull(message = "El ID de categoría es obligatorio")
    private Integer idCategoria;

    @NotBlank(message = "El SKU es obligatorio")
    @Size(max = 30, message = "El SKU no puede superar los 30 caracteres")
    private String sku;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Integer precioClp;
}
