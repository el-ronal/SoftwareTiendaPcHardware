package cl.pchardware.garantias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TicketGarantiaRequest {

    @NotNull(message = "El ID de pedido es obligatorio")
    private Integer idPedido;

    @NotBlank(message = "El SKU del producto es obligatorio")
    @Size(max = 30)
    private String skuProducto;

    @NotBlank(message = "El motivo del cliente es obligatorio")
    @Size(max = 255)
    private String motivoCliente;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20)
    private String estado;
}
