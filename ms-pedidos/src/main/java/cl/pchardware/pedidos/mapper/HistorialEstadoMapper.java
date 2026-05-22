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

    // Transforma el Request a Entidad. Ignoramos campos autogenerados y relaciones
    // directas del service.
    @Mapping(target = "idHistorial", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "fechaCambio", ignore = true)
    HistorialEstado toEntity(HistorialEstadoRequest request);

    // Transforma la Entidad a Response mapeando de forma explícita el id del pedido
    // relacionado.
    @Mapping(target = "idPedido", source = "pedido.idPedido")
    HistorialEstadoResponse toResponse(HistorialEstado historialEstado);

    List<HistorialEstadoResponse> toResponseList(List<HistorialEstado> historiales);

    // Actualización in-place para la trazabilidad si fuese necesario corregir un
    // registro.
    @Mapping(target = "idHistorial", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "fechaCambio", ignore = true)
    void updateEntity(HistorialEstadoRequest request, @MappingTarget HistorialEstado historialEstado);
}
