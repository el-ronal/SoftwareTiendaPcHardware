package cl.pchardware.pedidos.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class PedidoResponse {
    private Long idPedido;
    private Integer idUsuario;
    private LocalDateTime fechaCreacion;
    private String estado;
    private Integer totalClp;
    
    private List<DetallePedidoResponse> detalles;
    private List<HistorialEstadoResponse> historialEstados;
}
