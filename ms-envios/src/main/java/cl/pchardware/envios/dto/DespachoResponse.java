package cl.pchardware.envios.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DespachoResponse {
    private Integer idDespacho;
    private DireccionEnvioResponse direccionEnvio;
    private CourierResponse courier;
    private String codigoSeguimiento;
    private String estadoLogistico;
}
