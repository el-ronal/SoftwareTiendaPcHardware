package cl.pchardware.stock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MovimientoRequest {
    @NotNull(message = "El ID del inventario es obligatorio")
    private Long idInventario;
    
    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(regexp = "ENTRADA|SALIDA|AJUSTE", message = "El tipo debe ser ENTRADA, SALIDA o AJUSTE")
    private String tipoMovimiento;

    @NotNull(message = "La cantidad de variación es obligatoria")
    private Integer cantidadVariacion;
}
