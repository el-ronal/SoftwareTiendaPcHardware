package cl.pchardware.tasacion.dto;

import java.time.LocalDateTime;
import lombok.Data;
import cl.pchardware.tasacion.model.EstadoOferta;

@Data
public class OfertaCompraResponse {

    private Integer idOferta;
    private Integer idEvaluacion; // Viene de evaluacionTecnica.idEvaluacion
    private Integer montoOfrecidoClp;
    private EstadoOferta estadoOferta;
    private LocalDateTime fechaEmision;
}