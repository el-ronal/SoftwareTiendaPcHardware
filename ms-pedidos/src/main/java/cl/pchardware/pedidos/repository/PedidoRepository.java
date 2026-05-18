package cl.pchardware.pedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.pedidos.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    Optional<Pedido> findById(Integer IdPedido);
    // Basado en el índice idx_pedido_usuario
    List<Pedido> findByIdUsuario(Integer idUsuario);
    
    // Basado en el índice idx_pedido_estado
    List<Pedido> findByEstado(String estado);
}
