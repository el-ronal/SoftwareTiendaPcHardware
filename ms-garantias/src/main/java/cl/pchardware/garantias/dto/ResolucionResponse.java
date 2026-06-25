package cl.pchardware.garantias.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResolucionResponse {

    private Integer idResolucion;
    private Integer idInspeccion;
    private String accionTomada;
    private LocalDateTime fechaCierre;
}