package cl.pchardware.armado.dto;

import lombok.Data;

@Data
public class PedidoResponse { // Modificado: DTO espejo para interpretar la respuesta desde ms-pedidos
    private Integer idPedido;
    private String estado;
    private Integer totalClp;
}
