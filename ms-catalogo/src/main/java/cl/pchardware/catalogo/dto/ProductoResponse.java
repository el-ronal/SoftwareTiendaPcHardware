package cl.pchardware.catalogo.dto;

import lombok.Data;

@Data
public class ProductoResponse {

    private String sku;
    private Integer precioClp;
    private MarcaResponse marca;
    private CategoriaResponse categoria;
}
