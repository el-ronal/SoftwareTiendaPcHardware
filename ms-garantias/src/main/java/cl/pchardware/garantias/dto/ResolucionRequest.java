package cl.pchardware.garantias.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResolucionRequest {

    private Integer idInspeccion;
    private String accionTomada;
}