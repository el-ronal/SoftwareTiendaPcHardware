package cl.pchardware.catalogo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "producto",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_producto_sku", columnNames = "sku")
    },
    indexes = {
        @Index(name = "idx_producto_marca", columnList = "id_marca"),
        @Index(name = "idx_producto_categoria", columnList = "id_categoria")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto", nullable = false)
    private Long idProducto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @Column(name = "sku", nullable = false, length = 30, unique = true)
    private String sku;

    @Column(name = "precio_clp", nullable = false)
    private Integer precioClp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return idProducto != null && idProducto.equals(producto.getIdProducto());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}