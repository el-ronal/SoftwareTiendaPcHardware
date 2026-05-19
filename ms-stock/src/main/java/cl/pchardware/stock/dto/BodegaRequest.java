package cl.pchardware.stock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BodegaRequest {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 15, message = "El código no puede superar los 15 caracteres")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotNull(message = "El tipo es obligatorio")
    private String tipo;
}
