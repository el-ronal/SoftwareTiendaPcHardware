package cl.pchardware.tasacion.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "evaluacion_tecnica", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id_solicitud")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluacionTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion", nullable = false)
    private Integer idEvaluacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", referencedColumnName = "id_solicitud", nullable = false, unique = true)
    private SolicitudTasacion solicitudTasacion;

    @Column(name = "id_tasador", nullable = false)
    private Integer idTasador;

    @Column(name = "puntaje_condicion", nullable = false)
    private Integer puntajeCondicion;

    @Column(name = "observaciones", length = 255)
    private String observaciones;

    @OneToOne(mappedBy = "evaluacionTecnica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private OfertaCompra ofertaCompra;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluacionTecnica that = (EvaluacionTecnica) o;
        return idEvaluacion != null && Objects.equals(idEvaluacion, that.idEvaluacion);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}