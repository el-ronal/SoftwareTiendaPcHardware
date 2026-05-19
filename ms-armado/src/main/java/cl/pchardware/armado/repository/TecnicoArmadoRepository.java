package cl.pchardware.armado.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.armado.model.TecnicoArmado;

@Repository
public interface TecnicoArmadoRepository extends JpaRepository<TecnicoArmado, Integer> {

    Optional<TecnicoArmado> findByIdUsuario(Integer idUsuario);

    List<TecnicoArmado> findByActivo(Boolean activo);
}
