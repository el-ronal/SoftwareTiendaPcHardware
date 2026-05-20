package cl.pchardware.pedidos.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.pedidos.dto.DetallePedidoRequest;
import cl.pchardware.pedidos.dto.DetallePedidoResponse;
import cl.pchardware.pedidos.mapper.DetallePedidoMapper;
import cl.pchardware.pedidos.model.DetallePedido;
import cl.pchardware.pedidos.model.Pedido;
import cl.pchardware.pedidos.repository.DetallePedidoRepository;
import cl.pchardware.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de aplicar las reglas de negocio de los Detalles de Pedidos:
 * - Valida la existencia del Pedido padre antes de asociar líneas de compra.
 * - Gestiona el ciclo de vida de los productos vinculados a cada transacción.
 */
@Service
@RequiredArgsConstructor
public class DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final DetallePedidoMapper detallePedidoMapper;

    public List<DetallePedidoResponse> findAll() {
        return detallePedidoMapper.toResponseList(detallePedidoRepository.findAll());
    }

    public DetallePedidoResponse findById(Integer id) {
        return detallePedidoMapper.toResponse(getDetallePedidoById(id));
    }

    @Transactional
    public DetallePedidoResponse create(DetallePedidoRequest request) {
        Integer idPedido = Objects.requireNonNull(request.getIdPedido(), "idPedido");
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido", "ID", idPedido));
        
        DetallePedido detalle = Objects.requireNonNull(detallePedidoMapper.toEntity(request), "detalle");
        detalle.setPedido(pedido);
        return detallePedidoMapper.toResponse(detallePedidoRepository.save(Objects.requireNonNull(detalle, "detalle")));
    }

    @Transactional
    public DetallePedidoResponse update(Integer id, DetallePedidoRequest request) {
        DetallePedido detalle = getDetallePedidoById(id);
        Integer idPedido = Objects.requireNonNull(request.getIdPedido(), "idPedido");
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido", "ID", idPedido));
        
        detallePedidoMapper.updateEntity(request, detalle);
        detalle.setPedido(pedido);
        return detallePedidoMapper.toResponse(detallePedidoRepository.save(Objects.requireNonNull(detalle, "detalle")));
    }

    @Transactional
    public void deleteById(Integer id) {
        DetallePedido detalle = getDetallePedidoById(id);
        detallePedidoRepository.delete(Objects.requireNonNull(detalle, "detalle"));
    }

    private DetallePedido getDetallePedidoById(Integer id) {
        Objects.requireNonNull(id, "id");
        return detallePedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DetallePedido", "ID", id));
    }
}
