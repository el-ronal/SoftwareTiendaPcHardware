package cl.pchardware.armado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TecnicoArmadoRequest {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer idUsuario;

    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 30)
    private String especialidad;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}
