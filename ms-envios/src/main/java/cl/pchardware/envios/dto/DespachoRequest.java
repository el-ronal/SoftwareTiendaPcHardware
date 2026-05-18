package cl.pchardware.envios.dto;

import jakarta.validation.constraints.NotBlank;
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

    @NotNull(message = "El ID de dirección es obligatorio")
    private Integer idDireccion;

    @NotNull(message = "El ID de courier es obligatorio")
    private Integer idCourier;

    @Size(max = 50, message = "El código de seguimiento no puede superar los 50 caracteres")
    private String codigoSeguimiento;

    @NotBlank(message = "El estado logístico es obligatorio")
    @Size(max = 20, message = "El estado logístico no puede superar los 20 caracteres")
    private String estadoLogistico;
}
