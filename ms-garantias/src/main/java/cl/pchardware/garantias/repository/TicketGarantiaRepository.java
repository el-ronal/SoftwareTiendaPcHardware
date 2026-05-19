package cl.pchardware.garantias.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.garantias.model.TicketGarantia;

@Repository
public interface TicketGarantiaRepository extends JpaRepository<TicketGarantia, Integer> {

    List<TicketGarantia> findByIdPedido(Integer idPedido);

    List<TicketGarantia> findByEstado(String estado);
}
