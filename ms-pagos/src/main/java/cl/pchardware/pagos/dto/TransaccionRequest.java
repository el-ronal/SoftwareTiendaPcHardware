package cl.pchardware.pagos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionRequest {

    @NotNull(message = "El ID del pedido es obligatorio")
    private Integer idPedido;

    @NotNull(message = "El ID del método de pago es obligatorio")
    private Integer idMetodo;

    @NotNull(message = "El monto es obligatorio")
    @Min(value = 0, message = "El monto debe ser mayor o igual a 0")
    private Integer montoClp;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(PENDIENTE|APROBADA|RECHAZADA|REEMBOLSADA)$", message = "El estado debe ser PENDIENTE, APROBADA, RECHAZADA o REEMBOLSADA")
    private String estado;
}
