package cl.pchardware.pedidos.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(
    name = "detalle_pedido",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_pedido_sku", columnNames = {"id_pedido", "sku_producto"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle", nullable = false)
    private Integer idDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false, foreignKey = @ForeignKey(name = "fk_detalle_pedido"))
    private Pedido pedido;

    @Column(name = "sku_producto", nullable = false, length = 30)
    private String skuProducto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Integer precioUnitario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetallePedido that = (DetallePedido) o;
        return idDetalle != null && idDetalle.equals(that.idDetalle);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
