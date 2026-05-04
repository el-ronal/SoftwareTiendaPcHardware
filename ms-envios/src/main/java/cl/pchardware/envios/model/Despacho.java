package cl.pchardware.envios.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "despacho", uniqueConstraints = {
    @UniqueConstraint(columnNames = "codigo_seguimiento")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Despacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despacho")
    private Integer idDespacho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_direccion", nullable = false)
    private DireccionEnvio direccionEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_courier", nullable = false)
    private Courier courier;

    @Column(name = "codigo_seguimiento", length = 50)
    private String codigoSeguimiento;

    @Column(name = "estado_logistico", nullable = false, length = 20)
    private String estadoLogistico;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Despacho despacho = (Despacho) o;
        return Objects.equals(idDespacho, despacho.idDespacho);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDespacho);
    }
}