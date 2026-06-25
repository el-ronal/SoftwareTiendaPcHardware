package cl.pchardware.devoluciones.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotaCreditoResponse {

    private Integer idNota;
    private Integer idRecepcion;
    private Integer montoClp;
    private String estadoSii;
}