package cl.pchardware.tasacion.dto;

import lombok.Data;

@Data
public class EvaluacionTecnicaResponse {

    private Integer idEvaluacion;
    private Integer idSolicitud; // Viene de solicitudTasacion.idSolicitud
    private Integer idTasador;
    private Integer puntajeCondicion;
    private String observaciones;
    private Integer idOfertaCompra; // Viene de ofertaCompra.idOferta
}