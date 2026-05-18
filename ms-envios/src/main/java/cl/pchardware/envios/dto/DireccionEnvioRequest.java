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
public class DireccionEnvioRequest {

    @NotNull(message = "El ID de pedido es obligatorio")
    private Integer idPedido;

    @NotBlank(message = "La calle y número son obligatorios")
    @Size(max = 100, message = "La calle y número no pueden superar los 100 caracteres")
    private String calleNumero;

    @NotBlank(message = "La comuna es obligatoria")
    @Size(max = 50, message = "La comuna no puede superar los 50 caracteres")
    private String comuna;

    @NotBlank(message = "La región es obligatoria")
    @Size(max = 50, message = "La región no puede superar los 50 caracteres")
    private String region;
}
