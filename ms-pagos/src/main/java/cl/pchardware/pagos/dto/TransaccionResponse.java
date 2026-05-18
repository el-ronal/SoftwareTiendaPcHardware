package cl.pchardware.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionResponse {
    private Integer idTransaccion;
    private Integer idPedido;
    private Integer idMetodo;
    private Integer montoClp;
    private String estado;
}
