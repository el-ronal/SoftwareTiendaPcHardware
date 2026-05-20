package cl.pchardware.envios.dto;

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
public class CourierResponse {

    private Integer idCourier;
    private String codigo;
    private String nombreEmpresa;
    private String urlRastreo;
}
