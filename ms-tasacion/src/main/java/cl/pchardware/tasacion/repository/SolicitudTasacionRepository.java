package cl.pchardware.tasacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.tasacion.model.EstadoSolicitud;
import cl.pchardware.tasacion.model.SolicitudTasacion;

@Repository
public interface SolicitudTasacionRepository extends JpaRepository<SolicitudTasacion, Integer> {

    List<SolicitudTasacion> findByIdUsuario(Integer idUsuario);

    List<SolicitudTasacion> findByEstadoSolicitud(EstadoSolicitud estado);
}
