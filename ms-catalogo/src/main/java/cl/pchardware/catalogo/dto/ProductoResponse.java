package cl.pchardware.catalogo.dto;

import lombok.Data;

@Data
public class ProductoResponse {

    private Integer idProducto;
    private Integer idMarca;
    private String nombreMarca;
    private Integer idCategoria;
    private String nombreCategoria;
    private String sku;
    private Integer precioClp;
}
