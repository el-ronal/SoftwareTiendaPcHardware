package cl.pchardware.armado.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResultadoTestingRequest {

    @NotNull(message = "El ID de la orden no puede ser nulo")
    private Integer idOrden; // Se mapeará a orden.idOrden

    @NotNull(message = "La temperatura máxima del CPU es obligatoria")
    private Integer tempMaxCpu;

    // Este campo puede ser nulo según tu modelo
    private Integer puntajeBenchmark;

    @NotNull(message = "El estado de aprobación es obligatorio")
    private Boolean aprobado;
}