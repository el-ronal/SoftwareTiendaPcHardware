package cl.pchardware.envios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class CourierResponse extends RepresentationModel<CourierResponse> {

    private Integer idCourier;
    private String codigo;
    private String nombreEmpresa;
    private String urlRastreo;
}
