package cl.pchardware.pedidos.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.pedidos.dto.HistorialEstadoRequest;
import cl.pchardware.pedidos.dto.HistorialEstadoResponse;
import cl.pchardware.pedidos.mapper.HistorialEstadoMapper;
import cl.pchardware.pedidos.model.HistorialEstado;
import cl.pchardware.pedidos.model.Pedido;
import cl.pchardware.pedidos.repository.HistorialEstadoRepository;
import cl.pchardware.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la auditoría y trazabilidad del ciclo de vida de los
 * pedidos:
 * - Registra los saltos históricos de estados de forma consistente.
 */
@Service
@RequiredArgsConstructor
public class HistorialEstadoService {

    private final HistorialEstadoRepository historialEstadoRepository;
    private final PedidoRepository pedidoRepository;
    private final HistorialEstadoMapper historialEstadoMapper;

    public List<HistorialEstadoResponse> findAll() {
        return historialEstadoMapper.toResponseList(historialEstadoRepository.findAll());
    }

    public HistorialEstadoResponse findById(Integer id) {
        return historialEstadoMapper.toResponse(getHistorialEstadoById(id));
    }

    @Transactional
    public HistorialEstadoResponse create(HistorialEstadoRequest request) {
        Integer idPedido = Objects.requireNonNull(request.getIdPedido(), "idPedido");
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido", "ID", idPedido));

        HistorialEstado historial = Objects.requireNonNull(historialEstadoMapper.toEntity(request), "historial");
        historial.setPedido(pedido);
        return historialEstadoMapper.toResponse(historialEstadoRepository.save(Objects.requireNonNull(historial, "historial")));
    }

    @Transactional
    public void deleteById(Integer id) {
        HistorialEstado historial = getHistorialEstadoById(id);
        historialEstadoRepository.delete(Objects.requireNonNull(historial, "historial"));
    }

    private HistorialEstado getHistorialEstadoById(Integer id) {
        Objects.requireNonNull(id, "id");
        return historialEstadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("HistorialEstado", "ID", id));
    }
}
