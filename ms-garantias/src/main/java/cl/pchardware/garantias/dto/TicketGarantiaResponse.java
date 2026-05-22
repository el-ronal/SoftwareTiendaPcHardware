package cl.pchardware.garantias.dto;

import lombok.Data;

@Data
public class TicketGarantiaResponse {
    private Integer idTicket;
    private Integer idPedido;
    private String skuProducto;
    private String motivoCliente;
    private String estado;
}
