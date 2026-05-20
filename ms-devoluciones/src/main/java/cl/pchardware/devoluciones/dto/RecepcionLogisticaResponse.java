package cl.pchardware.devoluciones.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecepcionLogisticaResponse {

    private Integer idRecepcion;
    private Integer idDevolucion;
    private String estadoCaja;
    private Boolean aptoReventa;
    private Integer idNotaCredito;
}