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

    @Mapping(target = "idDetalle", ignore = true)
    @Mapping(target = "pedido", ignore = true) // La relación con Pedido se asigna en el Service
    DetallePedido toEntity(DetallePedidoRequest request);

    DetallePedidoResponse toResponse(DetallePedido detalle);

    List<DetallePedidoResponse> toResponseList(List<DetallePedido> detalles);

    @Mapping(target = "idDetalle", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    void updateEntity(DetallePedidoRequest request, @MappingTarget DetallePedido detalle);
}
