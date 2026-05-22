package cl.pchardware.garantias.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Table(name = "inspeccion_tecnica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InspeccionTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inspeccion", nullable = false)
    private Integer idInspeccion;

    @OneToOne
    @JoinColumn(name = "id_ticket", referencedColumnName = "id_ticket", unique = true, nullable = false)
    private TicketGarantia ticketGarantia;

    @Column(name = "id_tecnico", nullable = false)
    private Integer idTecnico;

    @Column(name = "aplica_garantia", nullable = false)
    private Boolean aplicaGarantia;

    @Column(name = "detalle_tecnico", nullable = false, length = 255)
    private String detalleTecnico;

    @OneToOne(mappedBy = "inspeccionTecnica", cascade = CascadeType.ALL)
    private Resolucion resolucion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InspeccionTecnica that = (InspeccionTecnica) o;
        return Objects.equals(idInspeccion, that.idInspeccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInspeccion);
    }
}