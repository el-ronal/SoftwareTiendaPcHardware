package cl.pchardware.pedidos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.pedidos.dto.HistorialEstadoRequest;
import cl.pchardware.pedidos.dto.HistorialEstadoResponse;
import cl.pchardware.pedidos.model.HistorialEstado;

@Mapper(componentModel = "spring")
public interface HistorialEstadoMapper {

    @Mapping(target = "idHistorial", ignore = true)
    @Mapping(target = "pedido", ignore = true) // Se asigna en el Service
    @Mapping(target = "fechaCambio", ignore = true) // Generado por JPA Auditing o BD
    HistorialEstado toEntity(HistorialEstadoRequest request);

    HistorialEstadoResponse toResponse(HistorialEstado historial);

    List<HistorialEstadoResponse> toResponseList(List<HistorialEstado> historiales);

    @Mapping(target = "idHistorial", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "fechaCambio", ignore = true)
    void updateEntity(HistorialEstadoRequest request, @MappingTarget HistorialEstado historial);
}
