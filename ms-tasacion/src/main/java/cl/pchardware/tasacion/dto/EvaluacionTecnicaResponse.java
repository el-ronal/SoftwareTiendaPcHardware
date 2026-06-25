package cl.pchardware.tasacion.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class EvaluacionTecnicaResponse extends RepresentationModel<EvaluacionTecnicaResponse> {

    private Integer idEvaluacion;
    private Integer idSolicitud; // Viene de solicitudTasacion.idSolicitud
    private Integer idTasador;
    private Integer puntajeCondicion;
    private String observaciones;
    private Integer idOfertaCompra; // Viene de ofertaCompra.idOferta
}