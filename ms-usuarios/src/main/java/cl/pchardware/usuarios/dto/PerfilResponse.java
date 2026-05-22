package cl.pchardware.usuarios.dto;

import lombok.Data;

@Data
public class PerfilResponse {
    private String rut;
    private String nombreCompleto;
    private String telefono;
}