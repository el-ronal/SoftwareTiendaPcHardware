package cl.pchardware.pagos.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReembolsoResponse {
    private Integer idReembolso;
    private Integer idTransaccion;
    private Integer montoDevolucion;
    private String motivo;
    private LocalDateTime fechaProceso;
}
