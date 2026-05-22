package cl.pchardware.tasacion.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EvaluacionTecnicaRequest {

    @NotNull(message = "El ID de la solicitud no puede ser nulo")
    private Integer idSolicitud; // Se mapeará a solicitudTasacion.idSolicitud

    @NotNull(message = "El ID del tasador es obligatorio")
    private Integer idTasador;

    @NotNull(message = "El puntaje de condición es obligatorio")
    private Integer puntajeCondicion;

    @Size(max = 255, message = "Las observaciones no pueden exceder los 255 caracteres")
    private String observaciones;
}