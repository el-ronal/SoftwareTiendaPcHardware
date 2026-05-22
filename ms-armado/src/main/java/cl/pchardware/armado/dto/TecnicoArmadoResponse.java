package cl.pchardware.armado.dto;

import lombok.Data;

@Data
public class TecnicoArmadoResponse {
    private Integer idTecnico;
    private Integer idUsuario;
    private String especialidad;
    private Boolean activo;
}
