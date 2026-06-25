package cl.pchardware.soporte.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class TicketSoporteResponse extends RepresentationModel<TicketSoporteResponse> {
    private Integer idTicket;
    private Integer idUsuario;
    private String categoria;
    private String estado;
    private LocalDateTime fechaApertura;
}
