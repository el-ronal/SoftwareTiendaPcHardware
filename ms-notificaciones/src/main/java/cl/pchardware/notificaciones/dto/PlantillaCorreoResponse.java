package cl.pchardware.notificaciones.dto;

import lombok.Data;

@Data
public class PlantillaCorreoResponse {
    private Integer idPlantilla;
    private String codigoEvento;
    private String asunto;
    private String cuerpoHtml;
}
