package cl.pchardware.armado.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.armado.model.OrdenEnsamble;

@Repository
public interface OrdenEnsambleRepository extends JpaRepository<OrdenEnsamble, Integer> {

    Optional<OrdenEnsamble> findByIdPedido(Integer idPedido);

    List<OrdenEnsamble> findByEstado(String estado);

    List<OrdenEnsamble> findByTecnico_IdTecnico(Integer idTecnico);
}
