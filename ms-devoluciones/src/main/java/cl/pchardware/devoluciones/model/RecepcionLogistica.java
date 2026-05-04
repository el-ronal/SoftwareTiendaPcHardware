package cl.pchardware.devoluciones.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "recepcion_logistica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecepcionLogistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recepcion", nullable = false)
    private Integer idRecepcion;

    @OneToOne
    @JoinColumn(name = "id_devolucion", referencedColumnName = "id_devolucion", unique = true, nullable = false)
    private SolicitudDevolucion solicitudDevolucion;

    @Column(name = "estado_caja", nullable = false, length = 20)
    private String estadoCaja;

    @Column(name = "apto_reventa", nullable = false)
    private Boolean aptoReventa;

    @OneToOne(mappedBy = "recepcionLogistica", cascade = CascadeType.ALL)
    private NotaCredito notaCredito;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecepcionLogistica that = (RecepcionLogistica) o;
        return Objects.equals(idRecepcion, that.idRecepcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRecepcion);
    }
}