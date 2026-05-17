package cl.pchardware.pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.pedidos.model.HistorialEstado;

@Repository
public interface HistorialEstadoRepository extends JpaRepository<HistorialEstado, Long> {
    
    // Basado en el índice idx_historial_pedido
    List<HistorialEstado> findByPedido_IdPedido(Long idPedido);
    
    // Para buscar el historial ordenado por fecha de cambio
    List<HistorialEstado> findByPedido_IdPedidoOrderByFechaCambioDesc(Long idPedido);
}
