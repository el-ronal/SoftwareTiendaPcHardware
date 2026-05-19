package cl.pchardware.stock.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovimientoRequest {

    @NotNull(message = "El ID de inventario es obligatorio")
    private Integer idInventario;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    private String tipoMovimiento;

    @NotNull(message = "La cantidad de variación es obligatoria")
    private Integer cantidadVariacion;
}
