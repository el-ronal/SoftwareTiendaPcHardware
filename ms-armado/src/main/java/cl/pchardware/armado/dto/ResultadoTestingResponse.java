package cl.pchardware.armado.dto;

import lombok.Data;

@Data
public class ResultadoTestingResponse {

    private Integer idResultado;
    private Integer idOrden; // Viene de orden.idOrden
    private Integer tempMaxCpu;
    private Integer puntajeBenchmark;
    private Boolean aprobado;
}