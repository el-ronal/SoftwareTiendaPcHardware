package cl.pchardware.pedidos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.pedidos.dto.PedidoRequest;
import cl.pchardware.pedidos.dto.PedidoResponse;
import cl.pchardware.pedidos.mapper.PedidoMapper;
import cl.pchardware.pedidos.model.HistorialEstado;
import cl.pchardware.pedidos.model.Pedido;
import cl.pchardware.pedidos.repository.HistorialEstadoRepository;
import cl.pchardware.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
/**
 * Servicio encargado de gestionar las operaciones y reglas de negocio para
 * Pedidos.
 * - Actúa como clase directa (@Service).
 * - Utiliza MapStruct para la conversión de DTOs y entidades.
 * - Gestiona la asignación manual de los detalles del pedido para mantener la
 * integridad bidireccional.
 * - Registra trazas automáticas en el historial ante cualquier cambio o
 * inicialización de estado.
 */
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final HistorialEstadoRepository historialEstadoRepository;
    private final PedidoMapper pedidoMapper;

    @Transactional(readOnly = true)
    public List<PedidoResponse> findAll() {
        return pedidoMapper.toResponseList(pedidoRepository.findAll());
    }

    @Transactional(readOnly = true)
    public PedidoResponse findById(long id) {
        return pedidoMapper.toResponse(getPedidoById(id));
    }

    @Transactional
    public PedidoResponse create(PedidoRequest request) {
        // 1. Convertir el Request DTO a la Entidad base
        Pedido pedido = pedidoMapper.toEntity(request);

        // 2. Asociar manualmente cada detalle con el pedido principal (Mapeo
        // bidireccional)
        if (pedido.getDetalles() != null) {
            pedido.getDetalles().forEach(detalle -> detalle.setPedido(pedido));
        }

        // 3. Persistir el Pedido (Los detalles se guardan en cascada gracias a
        // CascadeType.ALL)
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // 4. Registrar de manera automática el estado inicial en el Historial
        registrarHistorial(pedidoGuardado, null, pedidoGuardado.getEstado());

        return pedidoMapper.toResponse(pedidoGuardado);
    }

    @Transactional
    public PedidoResponse update(Long id, PedidoRequest request) {
        // 1. Obtener el pedido existente o lanzar excepción
        Pedido pedidoExistente = getPedidoById(id);
        String estadoAnterior = pedidoExistente.getEstado();

        // 2. Limpiar la colección de detalles antiguos para evitar duplicidad
        // (orphanRemoval se encargará del delete en BD)
        if (pedidoExistente.getDetalles() != null) {
            pedidoExistente.getDetalles().clear();
        }

        // 3. Volcar los nuevos datos del Request sobre la entidad gestionada por
        // Hibernate
        pedidoMapper.updateEntity(request, pedidoExistente);

        // 4. Reasignar la referencia del pedido en los nuevos detalles mapeados
        if (pedidoExistente.getDetalles() != null) {
            pedidoExistente.getDetalles().forEach(detalle -> detalle.setPedido(pedidoExistente));
        }

        // 5. Si existió una variación de estado, dejar constancia en el historial de
        // transiciones
        if (!estadoAnterior.equalsIgnoreCase(pedidoExistente.getEstado())) {
            registrarHistorial(pedidoExistente, estadoAnterior, pedidoExistente.getEstado());
        }

        // 6. Guardar los cambios y retornar la respuesta formateada
        Pedido pedidoActualizado = pedidoRepository.save(pedidoExistente);
        return pedidoMapper.toResponse(pedidoActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Pedido pedido = getPedidoById(id);
        // CascadeType.ALL removerá los detalles y el historial vinculados de forma
        // automática
        pedidoRepository.delete(pedido);
    }

    // ====== MÉTODOS PRIVADOS AUXILIARES (HELPERS) ======

    private Pedido getPedidoById(long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido", "ID", id));
    }

    private void registrarHistorial(Pedido pedido, String estadoAnterior, String estadoNuevo) {
        HistorialEstado historial = HistorialEstado.builder()
                .pedido(pedido)
                .estadoAnterior(estadoAnterior)
                .estadoNuevo(estadoNuevo)
                .build();
        historialEstadoRepository.save(historial);
    }
}
