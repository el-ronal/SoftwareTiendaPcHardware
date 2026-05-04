package cl.pchardware.devoluciones.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "nota_credito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotaCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota", nullable = false)
    private Integer idNota;

    @OneToOne
    @JoinColumn(name = "id_recepcion", referencedColumnName = "id_recepcion", unique = true, nullable = false)
    private RecepcionLogistica recepcionLogistica;

    @Column(name = "monto_clp", nullable = false)
    private Integer montoClp;

    @Column(name = "estado_sii", nullable = false, length = 20)
    private String estadoSii;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotaCredito that = (NotaCredito) o;
        return Objects.equals(idNota, that.idNota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNota);
    }
}