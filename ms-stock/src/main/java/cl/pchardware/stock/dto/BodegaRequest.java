// BodegaRequest.java y BodegaResponse.java
package cl.pchardware.stock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BodegaRequest {
    @NotBlank(message = "El código de la bodega es obligatorio")
    @Size(max = 15, message = "El código no puede superar los 15 caracteres")
    private String codigo;

    @NotBlank(message = "El nombre de la bodega es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El tipo de bodega es obligatorio")
    @Pattern(regexp = "NUEVOS|USADOS|MERMA", message = "El tipo debe ser NUEVOS, USADOS o MERMA")
    private String tipo;
}