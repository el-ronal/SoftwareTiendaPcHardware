package cl.pchardware.pedidos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.pedidos.dto.PedidoRequest;
import cl.pchardware.pedidos.dto.PedidoResponse;
import cl.pchardware.pedidos.model.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    // Transforma el Request (DTO) a la Entidad para guardarla en la BD.
    // Ignoramos id y campos autogenerados o relaciones complejas manejadas por el
    // Service.
    @Mapping(target = "idPedido", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "historialEstados", ignore = true)
    Pedido toEntity(PedidoRequest request);

    // Transforma la Entidad a Response para devolver al cliente.
    PedidoResponse toResponse(Pedido pedido);

    List<PedidoResponse> toResponseList(List<Pedido> pedidos);

    // Realiza una actualización sobre una entidad existente sin perder su identidad
    // ni alterar el ID.
    @Mapping(target = "idPedido", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "historialEstados", ignore = true)
    void updateEntity(PedidoRequest request, @MappingTarget Pedido pedido);
}
