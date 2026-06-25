package cl.pchardware.usuarios.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class UsuarioResponse extends RepresentationModel<UsuarioResponse> {
    private Long idUsuario;
    private String email;
    private String estado;
    
    // Respuestas anidadas que MapStruct llenará automáticamente
    private RolResponse rol;
    private PerfilResponse perfil;
}