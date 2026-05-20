package cl.pchardware.catalogo.mapper;

import cl.pchardware.catalogo.dto.ProductoRequest;
import cl.pchardware.catalogo.dto.ProductoResponse;
import cl.pchardware.catalogo.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring", uses = {MarcaMapper.class, CategoriaMapper.class})
public interface ProductoMapper {
    @Mapping(target = "idProducto", ignore = true)
    @Mapping(target = "marca", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Producto toEntity(ProductoRequest request);

    ProductoResponse toResponse(Producto producto);
    List<ProductoResponse> toResponseList(List<Producto> productos);

    @Mapping(target = "idProducto", ignore = true)
    @Mapping(target = "marca", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void updateEntity(ProductoRequest request, @MappingTarget Producto producto);
}