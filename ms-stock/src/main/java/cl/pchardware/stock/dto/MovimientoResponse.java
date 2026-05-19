package cl.pchardware.stock.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MovimientoResponse {

    private Integer idMovimiento;
    private Integer idInventario;
    private String skuProducto;
    private String tipoMovimiento;
    private Integer cantidadVariacion;
    private LocalDateTime fechaRegistro;
}
