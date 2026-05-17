package cl.pchardware.pedidos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.pchardware.pedidos.dto.PedidoRequest;
import cl.pchardware.pedidos.dto.PedidoResponse;
import cl.pchardware.pedidos.model.Pedido;

// Usamos componentModel = "spring" para poder inyectarlo como bean.
// Añadimos 'uses' para que MapStruct sepa cómo mapear las listas de detalles e historial.
@Mapper(componentModel = "spring", uses = {DetallePedidoMapper.class, HistorialEstadoMapper.class})
public interface PedidoMapper {

    // Transforma el Request (DTO) a la Entidad para guardarla en la BD.
    @Mapping(target = "idPedido", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true) // Generado por JPA Auditing o BD
    @Mapping(target = "detalles", ignore = true)      // Se gestiona en el Service para setear la referencia inversa
    @Mapping(target = "historialEstados", ignore = true)
    Pedido toEntity(PedidoRequest request);

    // Transforma la Entidad a Response para devolver al cliente.
    PedidoResponse toResponse(Pedido pedido);

    List<PedidoResponse> toResponseList(List<Pedido> pedidos);

    // Actualización sobre el Destino.
    @Mapping(target = "idPedido", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "historialEstados", ignore = true)
    void updateEntity(PedidoRequest request, @MappingTarget Pedido pedido);
}
