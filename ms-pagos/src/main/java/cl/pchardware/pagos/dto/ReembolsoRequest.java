package cl.pchardware.pagos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReembolsoRequest {

    @NotNull(message = "El ID de la transacción es obligatorio")
    private Integer idTransaccion;

    @NotNull(message = "El monto de devolución es obligatorio")
    @Min(value = 1, message = "El monto de devolución debe ser mayor a 0")
    private Integer montoDevolucion;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 100, message = "El motivo no puede superar los 100 caracteres")
    private String motivo;
}
