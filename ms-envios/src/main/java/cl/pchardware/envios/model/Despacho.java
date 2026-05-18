package cl.pchardware.envios.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "despacho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Despacho {

    public enum EstadoLogistico {
        PREPARACION, TRANSITO, REPARTO, ENTREGADO, EXTRAVIADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despacho", nullable = false)
    private Integer idDespacho;

    @ManyToOne
    @JoinColumn(name = "id_direccion", nullable = false)
    private DireccionEnvio direccionEnvio;

    @ManyToOne
    @JoinColumn(name = "id_courier", nullable = false)
    private Courier courier;

    @Column(name = "codigo_seguimiento", unique = true, length = 50)
    private String codigoSeguimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_logistico", nullable = false, length = 20)
    private EstadoLogistico estadoLogistico;

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
