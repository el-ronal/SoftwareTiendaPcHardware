package cl.pchardware.envios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionEnvioResponse {
    private Integer idDireccion;
    private Integer idPedido;
    private String calleNumero;
    private String comuna;
    private String region;
}
