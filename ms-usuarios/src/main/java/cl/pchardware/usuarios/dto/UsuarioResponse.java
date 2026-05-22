package cl.pchardware.usuarios.dto;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Long idUsuario;
    private String email;
    private String estado;
    
    // Respuestas anidadas que MapStruct llenará automáticamente
    private RolResponse rol;
    private PerfilResponse perfil;
}