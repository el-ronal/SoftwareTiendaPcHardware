// Movimiento.java
package cl.pchardware.stock.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "movimiento",
    indexes = {
        @Index(name = "idx_movimiento_inventario", columnList = "id_inventario")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento", nullable = false)
    private Long idMovimiento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_inventario", nullable = false)
    private Inventario inventario;

    @Column(name = "tipo_movimiento", nullable = false, length = 15)
    private String tipoMovimiento;

    @Column(name = "cantidad_variacion", nullable = false)
    private Integer cantidadVariacion;

    @CreatedDate
    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movimiento movimiento = (Movimiento) o;
        return idMovimiento != null && idMovimiento.equals(movimiento.idMovimiento);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}