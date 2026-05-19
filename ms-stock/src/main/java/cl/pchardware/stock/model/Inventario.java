// Inventario.java
package cl.pchardware.stock.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "inventario",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_bodega_sku", columnNames = {"id_bodega", "sku_producto"})
    },
    indexes = {
        @Index(name = "idx_inventario_sku", columnList = "sku_producto")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario", nullable = false)
    private Long idInventario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_bodega", nullable = false)
    private Bodega bodega;

    @Column(name = "sku_producto", nullable = false, length = 30)
    private String skuProducto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Builder.Default
    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimiento> movimientos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventario inventario = (Inventario) o;
        return idInventario != null && idInventario.equals(inventario.idInventario);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}