package cl.pchardware.armado.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TecnicoArmadoResponse extends RepresentationModel<TecnicoArmadoResponse> {
    private Integer idTecnico;
    private Integer idUsuario;
    private String especialidad;
    private Boolean activo;
}
