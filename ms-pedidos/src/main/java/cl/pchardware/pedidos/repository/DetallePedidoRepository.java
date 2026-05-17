package cl.pchardware.pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.pedidos.model.DetallePedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    
    // Basado en el índice idx_detalle_sku
    List<DetallePedido> findBySkuProducto(String skuProducto);
    
    // Para buscar todos los detalles de un pedido específico
    List<DetallePedido> findByPedido_IdPedido(Long idPedido);
}
