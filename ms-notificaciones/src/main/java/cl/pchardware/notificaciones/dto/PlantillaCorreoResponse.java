package cl.pchardware.notificaciones.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlantillaCorreoResponse extends RepresentationModel<PlantillaCorreoResponse> {
    private Integer idPlantilla;
    private String codigoEvento;
    private String asunto;
    private String cuerpoHtml;
}
