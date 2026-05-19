package cl.pchardware.devoluciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.devoluciones.model.SolicitudDevolucion;

@Repository
public interface SolicitudDevolucionRepository extends JpaRepository<SolicitudDevolucion, Integer> {

    List<SolicitudDevolucion> findByIdPedido(Integer idPedido);

    List<SolicitudDevolucion> findByEstado(String estado);
}
