package cl.pchardware.notificaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.notificaciones.model.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

    List<Mensaje> findByIdUsuario(Integer idUsuario);

    List<Mensaje> findByEstadoMensaje(String estado);
}
