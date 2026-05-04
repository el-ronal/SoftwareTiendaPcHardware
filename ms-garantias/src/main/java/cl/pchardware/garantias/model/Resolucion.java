package cl.pchardware.garantias.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "resolucion")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resolucion", nullable = false)
    private Integer idResolucion;

    @OneToOne
    @JoinColumn(name = "id_inspeccion", referencedColumnName = "id_inspeccion", unique = true, nullable = false)
    private InspeccionTecnica inspeccionTecnica;

    @Column(name = "accion_tomada", nullable = false, length = 30)
    private String accionTomada;

    @CreatedDate
    @Column(name = "fecha_cierre", updatable = false)
    private LocalDateTime fechaCierre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resolucion that = (Resolucion) o;
        return Objects.equals(idResolucion, that.idResolucion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idResolucion);
    }
}