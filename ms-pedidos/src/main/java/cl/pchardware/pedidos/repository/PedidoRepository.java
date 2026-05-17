package cl.pchardware.pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.pedidos.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Basado en el índice idx_pedido_usuario
    List<Pedido> findByIdUsuario(Integer idUsuario);
    
    // Basado en el índice idx_pedido_estado
    List<Pedido> findByEstado(String estado);
}
