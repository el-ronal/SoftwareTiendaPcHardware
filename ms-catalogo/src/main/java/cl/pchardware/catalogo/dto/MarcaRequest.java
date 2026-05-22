package cl.pchardware.catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MarcaRequest {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 15, message = "El código no puede superar los 15 caracteres")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;
}
