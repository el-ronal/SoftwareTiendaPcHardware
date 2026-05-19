package cl.pchardware.stock.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bodega")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bodega {
 
    public enum TipoBodega {
        NUEVOS,   // corregido (antes era NUEVO)
        USADOS,   // corregido (antes era USADO)
        MERMA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bodega", nullable = false)
    private Integer idBodega;

    @Column(name = "codigo", nullable = false, unique = true, length = 15)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoBodega tipo;

    @OneToMany(mappedBy = "bodega", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inventario> inventarios;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bodega bodega = (Bodega) o;
        return idBodega != null && Objects.equals(idBodega, bodega.idBodega);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}