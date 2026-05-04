package cl.pchardware.pagos.model;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reembolso")
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
    @JoinColumn(name = "id_transaccion", unique = true, nullable = false)
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
        return Objects.equals(idReembolso, reembolso.idReembolso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReembolso);
    }
}