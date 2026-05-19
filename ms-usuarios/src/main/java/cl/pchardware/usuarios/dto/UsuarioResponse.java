package cl.pchardware.usuarios.dto;

import lombok.Data;

@Data
public class UsuarioResponse {

    private Integer idUsuario;
    private Integer idRol;
    private String nombreRol;
    private String email;
    private String estado;
}
