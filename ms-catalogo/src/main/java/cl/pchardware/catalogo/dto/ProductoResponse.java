package cl.pchardware.catalogo.dto;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductoResponse extends RepresentationModel<ProductoResponse> {

    private String sku;
    private Integer precioClp;
    private MarcaResponse marca;
    private List<CategoriaResponse> categorias;
}
