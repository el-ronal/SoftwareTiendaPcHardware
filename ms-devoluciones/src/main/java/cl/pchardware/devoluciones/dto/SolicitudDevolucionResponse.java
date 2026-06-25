package cl.pchardware.devoluciones.dto;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SolicitudDevolucionResponse extends RepresentationModel<SolicitudDevolucionResponse> {
    private Integer idDevolucion;
    private Integer idPedido;
    private String motivo;
    private String estado;
    private LocalDateTime fechaSolicitud;
}
