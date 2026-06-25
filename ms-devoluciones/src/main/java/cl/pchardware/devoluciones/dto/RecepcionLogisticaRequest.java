package cl.pchardware.devoluciones.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecepcionLogisticaRequest {

    private Integer idDevolucion;
    private String estadoCaja;
    private Boolean aptoReventa;
}