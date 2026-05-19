package cl.pchardware.armado.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrdenEnsambleResponse {
    private Integer idOrden;
    private Integer idPedido;
    private Integer idTecnico;
    private String estado;
    private LocalDateTime fechaInicio;
}
