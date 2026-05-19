package cl.pchardware.stock.mapper;

import cl.pchardware.dto.InventarioRequest;
import cl.pchardware.dto.InventarioResponse;
import cl.pchardware.model.Inventario;
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