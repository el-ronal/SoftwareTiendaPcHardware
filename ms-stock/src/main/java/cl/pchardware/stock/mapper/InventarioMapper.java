package cl.pchardware.stock.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.stock.dto.InventarioRequest;
import cl.pchardware.stock.dto.InventarioResponse;
import cl.pchardware.stock.model.Inventario;

@Mapper(componentModel = "spring")
public interface InventarioMapper {

    @Mapping(target = "idInventario", ignore = true)
    @Mapping(target = "bodega", ignore = true)
    @Mapping(target = "movimientos", ignore = true)
    Inventario toEntity(InventarioRequest request);

    @Mapping(source = "bodega.idBodega", target = "idBodega")
    @Mapping(source = "bodega.nombre", target = "nombreBodega")
    InventarioResponse toResponse(Inventario inventario);

    List<InventarioResponse> toResponseList(List<Inventario> inventarios);

    @Mapping(target = "idInventario", ignore = true)
    @Mapping(target = "bodega", ignore = true)
    @Mapping(target = "movimientos", ignore = true)
    void updateEntity(InventarioRequest request, @MappingTarget Inventario inventario);
}
