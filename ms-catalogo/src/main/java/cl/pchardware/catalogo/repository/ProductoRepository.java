package cl.pchardware.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.catalogo.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    Optional<Producto> findBySku(String sku);

    boolean existsBySku(String sku);

    List<Producto> findByMarca_IdMarca(Integer idMarca);

    List<Producto> findByCategoria_IdCategoria(Integer idCategoria);
}
