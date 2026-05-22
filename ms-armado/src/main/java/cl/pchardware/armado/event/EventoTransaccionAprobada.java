package cl.pchardware.armado.event;

import lombok.Data;

@Data
public class EventoTransaccionAprobada {
    private Integer idPedido;
    private Integer montoClp;
    private String estado; // Vendrá como "APROBADA"
}