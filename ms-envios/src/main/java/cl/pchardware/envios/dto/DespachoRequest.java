package cl.pchardware.envios.dto;

import cl.pchardware.envios.model.Despacho;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class DespachoRequest {

    @NotNull(message = "El ID de la dirección de envío es obligatorio")
    private Integer idDireccion;

    @NotNull(message = "El ID del courier es obligatorio")
    private Integer idCourier;

    @Size(max = 50, message = "El código de seguimiento no puede superar los 50 caracteres")
    private String codigoSeguimiento;

    @NotNull(message = "El estado logístico es obligatorio")
    private Despacho.EstadoLogistico estadoLogistico;
}
