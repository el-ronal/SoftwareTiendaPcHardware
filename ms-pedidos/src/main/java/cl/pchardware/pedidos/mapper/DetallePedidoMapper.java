package cl.pchardware.pedidos.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.pedidos.dto.DetallePedidoRequest;
import cl.pchardware.pedidos.dto.DetallePedidoResponse;
import cl.pchardware.pedidos.model.DetallePedido;

@Mapper(componentModel = "spring")
public interface DetallePedidoMapper {

    // Transforma el Request a Entidad. Ignoramos la relación 'pedido' ya que el service
    // la resolverá buscando el Pedido correspondiente en la BD.
    @Mapping(target = "idDetalle", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    DetallePedido toEntity(DetallePedidoRequest request);

    // Transforma la Entidad a Response, extrayendo el idPedido de la entidad relacionada.
    @Mapping(target = "idPedido", source = "pedido.idPedido")
    DetallePedidoResponse toResponse(DetallePedido detallePedido);

    List<DetallePedidoResponse> toResponseList(List<DetallePedido> detalles);

    // Actualiza la entidad existente a partir de los datos modificados del request.
    @Mapping(target = "idDetalle", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    void updateEntity(DetallePedidoRequest request, @MappingTarget DetallePedido detallePedido);
}
