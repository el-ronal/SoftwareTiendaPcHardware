package cl.pchardware.soporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.soporte.model.MensajeTicket;

@Repository
public interface MensajeTicketRepository extends JpaRepository<MensajeTicket, Integer> {

    List<MensajeTicket> findByTicketSoporte_IdTicket(Integer idTicket);
}
