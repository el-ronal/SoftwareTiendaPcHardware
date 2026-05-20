package cl.pchardware.catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoriaRequest {

    @NotBlank(message = "El slug es obligatorio")
    @Pattern(regexp = "^[a-z0-9]+$", message = "El slug solo puede contener letras minúsculas y números, sin espacios ni caracteres especiales")
    @Size(max = 50, message = "El slug no puede superar los 50 caracteres")
    private String slug;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;
}
