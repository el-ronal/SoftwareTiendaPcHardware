package cl.pchardware.envios.dto;
import jakarta.validation.constraints.NotBlank;
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
public class CourierRequest {

    @NotBlank(message = "El código del courier es obligatorio")
    @Size(max = 15, message = "El código no puede superar los 15 caracteres")
    private String codigo;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 50, message = "El nombre de la empresa no puede superar los 50 caracteres")
    private String nombreEmpresa;

    @Size(max = 255, message = "La URL de rastreo no puede superar los 255 caracteres")
    private String urlRastreo;
}
