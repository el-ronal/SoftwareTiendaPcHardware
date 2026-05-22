package cl.pchardware.usuarios.dto;

import lombok.Data;

@Data
public class RolResponse {
    private Long idRol;
    private String nombre;
    private String descripcion;
}