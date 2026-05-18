package cl.pchardware.pedidos.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.pedidos.model.Pedido;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
