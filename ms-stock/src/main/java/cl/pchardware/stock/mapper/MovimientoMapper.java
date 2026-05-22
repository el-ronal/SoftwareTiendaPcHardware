package cl.pchardware.stock.mapper;

import cl.pchardware.stock.dto.MovimientoRequest;
import cl.pchardware.stock.dto.MovimientoResponse;
import cl.pchardware.stock.model.Movimiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    @Mapping(target = "idMovimiento", ignore = true)
    @Mapping(target = "inventario", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    Movimiento toEntity(MovimientoRequest request);

    MovimientoResponse toResponse(Movimiento movimiento);
    List<MovimientoResponse> toResponseList(List<Movimiento> movimientos);
}
