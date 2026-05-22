package cl.pchardware.tasacion.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import cl.pchardware.tasacion.model.EstadoOferta;

@Data
public class OfertaCompraRequest {

    @NotNull(message = "El ID de la evaluación técnica no puede ser nulo")
    private Integer idEvaluacion;

    @NotNull(message = "El monto ofrecido es obligatorio")
    @PositiveOrZero(message = "El monto ofrecido no puede ser negativo")
    private Integer montoOfrecidoClp;

    @NotNull(message = "El estado de la oferta es obligatorio")
    private EstadoOferta estadoOferta;
    
    // No incluimos 'fechaEmision' porque Spring Data JPA lo genera automaticamente con @CreatedDate :DDDD
}
