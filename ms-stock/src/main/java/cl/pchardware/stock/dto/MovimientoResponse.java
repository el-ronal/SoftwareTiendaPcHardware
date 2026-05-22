package cl.pchardware.stock.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovimientoResponse {
    private Long idMovimiento;
    private String tipoMovimiento;
    private Integer cantidadVariacion;
    private LocalDateTime fechaRegistro;
}