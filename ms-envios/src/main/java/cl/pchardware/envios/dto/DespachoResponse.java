package cl.pchardware.envios.dto;


import cl.pchardware.envios.model.Despacho;
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
public class DespachoResponse {

    private Integer idDespacho;
    private Integer idDireccion;
    private Integer idCourier;
    private String codigoSeguimiento;
    private Despacho.EstadoLogistico estadoLogistico;
}
