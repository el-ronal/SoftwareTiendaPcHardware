package cl.pchardware.pedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class DetallePedidoRequest {

    @NotNull(message = "El ID del pedido es obligatorio")
    private Integer idPedido;

    @NotBlank(message = "El SKU del producto es obligatorio")
    @Size(max = 30, message = "El SKU no puede superar los 30 caracteres")
    private String skuProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio unitario no puede ser negativo")
    private Integer precioUnitario;
}
