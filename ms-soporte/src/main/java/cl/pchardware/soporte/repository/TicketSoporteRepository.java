package cl.pchardware.soporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.soporte.model.TicketSoporte;

@Repository
public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Integer> {

    List<TicketSoporte> findByIdUsuario(Integer idUsuario);

    List<TicketSoporte> findByEstado(String estado);
}
