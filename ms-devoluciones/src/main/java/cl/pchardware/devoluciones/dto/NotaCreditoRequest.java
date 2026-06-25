package cl.pchardware.devoluciones.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotaCreditoRequest {

    private Integer idRecepcion;
    private Integer montoClp;
    private String estadoSii;
}