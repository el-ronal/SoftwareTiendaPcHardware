package cl.pchardware.armado.dto;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Long idUsuario;
    private String email;
    private String estado;
}