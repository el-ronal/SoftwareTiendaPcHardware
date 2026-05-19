package cl.pchardware.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.catalogo.dto.ProductoRequest;
import cl.pchardware.catalogo.dto.ProductoResponse;
import cl.pchardware.catalogo.model.Producto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    @Mapping(target = "idProducto", ignore = true)
    @Mapping(target = "marca", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Producto toEntity(ProductoRequest request);

    @Mapping(source = "marca.idMarca", target = "idMarca")
    @Mapping(source = "marca.nombre", target = "nombreMarca")
    @Mapping(source = "categoria.idCategoria", target = "idCategoria")
    @Mapping(source = "categoria.nombre", target = "nombreCategoria")
    ProductoResponse toResponse(Producto producto);

    List<ProductoResponse> toResponseList(List<Producto> productos);

    @Mapping(target = "idProducto", ignore = true)
    @Mapping(target = "marca", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void updateEntity(ProductoRequest request, @MappingTarget Producto producto);
}
