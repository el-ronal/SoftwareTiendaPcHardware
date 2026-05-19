package cl.pchardware.stock.dto;

import lombok.Data;

@Data
public class BodegaResponse {

    private Integer idBodega;
    private String codigo;
    private String nombre;
    private String tipo;
}
