package cl.pchardware.armado.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Table(name = "resultado_testing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoTesting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado")
    private Integer idResultado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden", nullable = false, unique = true)
    private OrdenEnsamble orden;

    @Column(name = "temp_max_cpu", nullable = false)
    private Integer tempMaxCpu;

    @Column(name = "puntaje_benchmark")
    private Integer puntajeBenchmark;

    @Column(name = "aprobado", nullable = false)
    private Boolean aprobado;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultadoTesting that = (ResultadoTesting) o;
        return Objects.equals(idResultado, that.idResultado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idResultado);
    }
}