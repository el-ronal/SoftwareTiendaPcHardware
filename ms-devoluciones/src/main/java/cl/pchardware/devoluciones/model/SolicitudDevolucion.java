package cl.pchardware.devoluciones.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "solicitud_devolucion")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudDevolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_devolucion", nullable = false)
    private Integer idDevolucion;

    @Column(name = "id_pedido", nullable = false)
    private Integer idPedido;

    @Column(name = "motivo", nullable = false, length = 25)
    private String motivo;

    @Column(name = "estado", length = 20)
    private String estado;

    @CreatedDate
    @Column(name = "fecha_solicitud", updatable = false)
    private LocalDateTime fechaSolicitud;

    @OneToOne(mappedBy = "solicitudDevolucion", cascade = CascadeType.ALL)
    private RecepcionLogistica recepcionLogistica;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolicitudDevolucion that = (SolicitudDevolucion) o;
        return Objects.equals(idDevolucion, that.idDevolucion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDevolucion);
    }
}