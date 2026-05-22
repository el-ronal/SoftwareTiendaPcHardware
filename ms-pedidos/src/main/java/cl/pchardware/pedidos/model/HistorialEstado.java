package cl.pchardware.pedidos.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_estado")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial", nullable = false)
    private Integer idHistorial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false, foreignKey = @ForeignKey(name = "fk_historial_pedido"))
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_anterior", length = 20)
    private Pedido.EstadoPedido estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_nuevo", nullable = false, length = 20)
    private Pedido.EstadoPedido estadoNuevo;

    @CreatedDate
    @Column(name = "fecha_cambio", nullable = false, updatable = false)
    private LocalDateTime fechaCambio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistorialEstado that = (HistorialEstado) o;
        return idHistorial != null && idHistorial.equals(that.idHistorial);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
