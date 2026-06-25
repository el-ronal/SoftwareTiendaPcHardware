package cl.pchardware.garantias.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TicketGarantiaResponse extends RepresentationModel<TicketGarantiaResponse> {
    private Integer idTicket;
    private Integer idPedido;
    private String skuProducto;
    private String motivoCliente;
    private String estado;
}
