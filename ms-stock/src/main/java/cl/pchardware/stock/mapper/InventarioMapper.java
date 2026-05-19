package cl.pchardware.stock.mapper;

import cl.pchardware.stock.dto.InventarioRequest;
import cl.pchardware.stock.dto.InventarioResponse;
import cl.pchardware.stock.model.Inventario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BodegaMapper.class})
public interface InventarioMapper {

    @Mapping(target = "idInventario", ignore = true)
    @Mapping(target = "bodega", ignore = true)
    @Mapping(target = "movimientos", ignore = true)
    Inventario toEntity(InventarioRequest request);

    InventarioResponse toResponse(Inventario inventario);
    List<InventarioResponse> toResponseList(List<Inventario> inventarios);
}