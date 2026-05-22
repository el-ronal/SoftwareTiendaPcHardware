// Bodega.java
package cl.pchardware.stock.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "bodega",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_bodega_codigo", columnNames = "codigo")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bodega", nullable = false)
    private Long idBodega;

    @Column(name = "codigo", nullable = false, length = 15, unique = true)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Builder.Default
    @OneToMany(mappedBy = "bodega", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventario> inventarios = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bodega bodega = (Bodega) o;
        return idBodega != null && idBodega.equals(bodega.idBodega);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}