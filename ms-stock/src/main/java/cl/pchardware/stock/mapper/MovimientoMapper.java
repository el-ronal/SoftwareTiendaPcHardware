package cl.pchardware.stock.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.pchardware.stock.dto.MovimientoRequest;
import cl.pchardware.stock.dto.MovimientoResponse;
import cl.pchardware.stock.model.Movimiento;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    @Mapping(target = "idMovimiento", ignore = true)
    @Mapping(target = "inventario", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    Movimiento toEntity(MovimientoRequest request);

    @Mapping(source = "inventario.idInventario", target = "idInventario")
    @Mapping(source = "inventario.skuProducto", target = "skuProducto")
    MovimientoResponse toResponse(Movimiento movimiento);

    List<MovimientoResponse> toResponseList(List<Movimiento> movimientos);
}
