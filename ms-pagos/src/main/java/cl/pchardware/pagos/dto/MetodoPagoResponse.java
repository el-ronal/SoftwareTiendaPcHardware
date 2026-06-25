package cl.pchardware.pagos.dto;

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
public class MetodoPagoResponse extends RepresentationModel<MetodoPagoResponse> {

    private Integer idMetodo;
    private String codigo;
    private String nombre;
    private Boolean activo;
}
