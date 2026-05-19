package cl.pchardware.catalogo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.catalogo.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {

    Optional<Marca> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);
}
