package cl.pchardware.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetodoPagoResponse {
    private Integer idMetodo;
    private String codigo;
    private String nombre;
    private Boolean activo;
}
