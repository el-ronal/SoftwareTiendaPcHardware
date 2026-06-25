package cl.pchardware.usuarios.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String token;

    @Builder.Default
    private String tokenType = "Bearer";

    private String email;

    private String nombre;

    /**Rol del usuario (ADMIN, TASADOR, CLIENTE) */
    private String rol;

    private long expiresIn; // Tiempo de expiración en segundos
}
