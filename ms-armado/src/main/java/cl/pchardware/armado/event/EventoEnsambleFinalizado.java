package cl.pchardware.armado.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Modificado: Se utiliza Lombok para reducir el código boilerplate
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventoEnsambleFinalizado {
    private Integer idOrden;
    private Integer idPedido;
    private String estadoFinal;
}