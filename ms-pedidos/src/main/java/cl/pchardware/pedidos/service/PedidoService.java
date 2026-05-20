package cl.pchardware.pedidos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.common.exception.ReferentialIntegrityException;
import cl.pchardware.pedidos.dto.PedidoRequest;
import cl.pchardware.pedidos.dto.PedidoResponse;
import cl.pchardware.pedidos.mapper.PedidoMapper;
import cl.pchardware.pedidos.model.Pedido;
import cl.pchardware.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de aplicar las reglas de negocio de Pedidos:
 * - Gestiona operaciones CRUD, validaciones de negocio y reglas de integridad.
 * - Maneja las asociaciones de detalle e historial y lanza excepciones personalizadas para casos de error específicos.
 * - Utiliza un mapper para convertir entre entidades y DTOs, manteniendo el código limpio y separado.
 * - Implementa transacciones para asegurar la consistencia de los datos en operaciones concurrentes o complejas.
 */
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    public List<PedidoResponse> findAll() {
        return pedidoMapper.toResponseList(pedidoRepository.findAll());
    }

    public PedidoResponse findById(Integer id) {
        return pedidoMapper.toResponse(getPedidoById(id));
    }

    @Transactional
    public PedidoResponse create(PedidoRequest request) {
        Pedido pedido = Objects.requireNonNull(new Pedido(), "pedido");
        pedidoMapper.updateEntity(request, pedido);
        return pedidoMapper.toResponse(pedidoRepository.save(Objects.requireNonNull(pedido, "pedido")));
    }

    @Transactional
    public PedidoResponse update(Integer id, PedidoRequest request) {
        Pedido pedido = getPedidoById(id);
        pedidoMapper.updateEntity(request, pedido);
        return pedidoMapper.toResponse(pedidoRepository.save(Objects.requireNonNull(pedido, "pedido")));
    }

    @Transactional
    public void deleteById(Integer id) {
        Pedido pedido = getPedidoById(id);
        List<String> tablasAsociadas = new ArrayList<>();
        
        if (pedido.getDetalles() != null && !pedido.getDetalles().isEmpty()) {
            tablasAsociadas.add("DetallePedido");
        }
        if (pedido.getHistorialEstados() != null && !pedido.getHistorialEstados().isEmpty()) {
            tablasAsociadas.add("HistorialEstado");
        }
        
        if (!tablasAsociadas.isEmpty()) {
            throw new ReferentialIntegrityException("Pedido", id, String.join(", ", tablasAsociadas));
        }
        
        pedidoRepository.delete(pedido);
    }

    private Pedido getPedidoById(Integer id) {
        Objects.requireNonNull(id, "id");
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido", "ID", id));
    }
}
