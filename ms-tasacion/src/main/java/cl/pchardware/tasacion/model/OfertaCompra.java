package cl.pchardware.tasacion.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "oferta_compra", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id_evaluacion")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oferta", nullable = false)
    private Integer idOferta;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evaluacion", referencedColumnName = "id_evaluacion", nullable = false, unique = true)
    private EvaluacionTecnica evaluacionTecnica;

    @Column(name = "monto_ofrecido_clp", nullable = false)
    private Integer montoOfrecidoClp;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_oferta", nullable = false, length = 20)
    private EstadoOferta estadoOferta;

    @CreatedDate
    @Column(name = "fecha_emision", updatable = false)
    private LocalDateTime fechaEmision;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfertaCompra that = (OfertaCompra) o;
        return idOferta != null && Objects.equals(idOferta, that.idOferta);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}