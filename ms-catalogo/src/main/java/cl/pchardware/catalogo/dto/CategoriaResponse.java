package cl.pchardware.catalogo.dto;

import lombok.Data;

@Data
public class CategoriaResponse {

    private Integer idCategoria;
    private String slug;
    private String nombre;
}
