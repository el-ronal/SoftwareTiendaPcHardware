package cl.pchardware.pagos.dto;

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
public class MetodoPagoResponse {

    private Integer idMetodo;
    private String codigo;
    private String nombre;
    private Boolean activo;
}
