package cl.pchardware.garantias.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InspeccionTecnicaResponse {

    private Integer idInspeccion;
    private Integer idTicket;
    private Integer idTecnico;
    private Boolean aplicaGarantia;
    private String detalleTecnico;
    private Integer idResolucion;
}