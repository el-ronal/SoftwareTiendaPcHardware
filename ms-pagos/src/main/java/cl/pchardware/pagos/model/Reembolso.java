package cl.pchardware.pagos.model;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "reembolso", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id_transaccion")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reembolso")
    private Integer idReembolso;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_transaccion", nullable = false, unique = true)
    private Transaccion transaccion;

    @Column(name = "monto_devolucion", nullable = false)
    private Integer montoDevolucion;

    @Column(name = "motivo", nullable = false, length = 100)
    private String motivo;

    @CreatedDate
    @Column(name = "fecha_proceso", updatable = false)
    private LocalDateTime fechaProceso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reembolso reembolso = (Reembolso) o;
        return idReembolso != null && idReembolso.equals(reembolso.idReembolso);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
